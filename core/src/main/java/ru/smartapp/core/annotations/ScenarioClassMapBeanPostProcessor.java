package ru.smartapp.core.annotations;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Search for all classes annotated with {@link ScenarioClass} and {@link ScenarioClassMap}.
 * All classes, annotated with {@link ScenarioClass} will store in bean with {@link ScenarioClassMap}
 * Class with {@link ScenarioClassMap} must has methods
 * get(String scenarioId)
 * put(@NotNull String scenarioId, @NotNull Class<? extends Scenario> scenarioClass)
 */

@Component
public class ScenarioClassMapBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationContext context;

    Map<String, ScenarioMap> scenarios = new HashMap<>();
    Map<String, Class<?>> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(ScenarioClass.class)) {
            ScenarioClass[] annotations = bean.getClass().getDeclaredAnnotationsByType(ScenarioClass.class);
            for (ScenarioClass annotation : annotations) {
                if (scenarios.containsKey(beanName)) {
                    throw new RuntimeException(String.format("Duplicate scenario' beanName '%s' with scenario's annotation values %s", beanName, Arrays.toString(annotation.value())));
                }
                scenarios.put(beanName, new ScenarioMap(bean.getClass(), annotation.value()));
            }
        }
        if (bean.getClass().isAnnotationPresent(ScenarioClassMap.class)) {
            map.put(beanName, bean.getClass());
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        if (scenarios.containsKey(beanName)) {
            for (Map.Entry<String, Class<?>> entry : map.entrySet()) {
                Class<?> scenarioMapClass = entry.getValue();
                Object scenarioMapBean = context.getBean(scenarioMapClass);
                Method get = scenarioMapBean.getClass().getDeclaredMethod("get", String.class);
                Method put = scenarioMapBean.getClass().getDeclaredMethod("put", String.class, Class.class);
                for (Map.Entry<String, ScenarioMap> scenarioEntry : scenarios.entrySet()) {
                    ScenarioMap map = scenarioEntry.getValue();
                    for (String scenarioId : map.annotationValues) {
                        try {
                            if (get.invoke(scenarioMapBean, scenarioId) == null) {
                                put.invoke(scenarioMapBean, scenarioId, map.beanClass);
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private static class ScenarioMap {
        public Class<?> beanClass;
        public String[] annotationValues;

        public ScenarioMap(Class<?> beanClass, String[] annotationValues) {
            this.beanClass = beanClass;
            this.annotationValues = annotationValues;
        }
    }

}
