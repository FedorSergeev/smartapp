package ru.smartapp.core;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smartapp.core.defaultanswers.NothingFound;
import ru.smartapp.core.intents.Scenario;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Service("scenarioExecutor")
public class ScenarioExecutor {
    private final Log log = LogFactory.getLog(getClass());
    private ScenariosMapping scenariosMapping;

    @Autowired
    public ScenarioExecutor(ScenariosMapping scenariosMapping) {
        this.scenariosMapping = scenariosMapping;
    }

    /**
     * todo javadoc
     *
     * @param someInfo
     * @return
     */
    public JsonNode run(JsonNode someInfo) {
        String scenarioId = someInfo.has("intent") ? someInfo.get("intent").asText() : "";
        Class<? extends Scenario> scenarioClass = scenariosMapping.get(scenarioId);
        if (scenarioClass == null) {
            log.error(String.format("There is no scenario with id %s", scenarioId));
            return new NothingFound().run();
        }
        ScenarioContext scenarioContext = new ScenarioContext(someInfo);
        Scenario scenario = buildScenario(scenarioClass, scenarioContext);
        if (scenario == null) {
            return new NothingFound().run();
        }
        return scenario.run(scenarioContext);
    }

    @Nullable
    /**
     * todo javadoc
     *
     * @param someInfo
     * @return
     */
    private Scenario buildScenario(Class<? extends Scenario> scenarioClass, ScenarioContext scenarioContext) {
        try {
            Constructor<?> ctor = scenarioClass.getConstructor(ScenarioContext.class);
            return (Scenario) ctor.newInstance(new Object[]{scenarioContext});
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Error on init scenario class", e);
        }
        return null;
    }

}
