package ru.smartapp.core.intents;

import org.json.JSONObject;
import ru.smartapp.core.ScenarioContext;

import java.util.Collections;

public class SomeDubmScenario implements Scenario{

    @Override
    public JSONObject run(ScenarioContext scenarioContext) {
        return new JSONObject(Collections.singletonMap("key", "value"));
    }

}
