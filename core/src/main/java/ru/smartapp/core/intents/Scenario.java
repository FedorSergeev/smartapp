package ru.smartapp.core.intents;

import org.json.JSONObject;
import ru.smartapp.core.ScenarioContext;

public interface Scenario {

    JSONObject run(ScenarioContext scenarioContext);

}
