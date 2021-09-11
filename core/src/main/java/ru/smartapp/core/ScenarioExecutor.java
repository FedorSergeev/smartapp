package ru.smartapp.core;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        Class<Scenario> scenarioClass = scenariosMapping.get(scenarioId);
        if (scenarioClass == null) {
            //TODO: send message NothingFound
            log.error(String.format("There is no scenario with id %s", scenarioId));
            return null;
        }
        ScenarioContext scenarioContext = new ScenarioContext(someInfo);
        Scenario scenario = buildScenario(scenarioClass, scenarioContext);
        JsonNode answer = scenario.run(scenarioContext);
        return answer;
    }

    private Scenario buildScenario(Class<Scenario> scenarioClass, ScenarioContext scenarioContext) {
        try {
            Constructor<?> ctor = scenarioClass.getConstructor(ScenarioContext.class);
            return (Scenario) ctor.newInstance(new Object[]{scenarioContext});
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Error while building scenario", e);
        }
        return null;
    }

}
