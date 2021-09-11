package ru.smartapp.core.intents;

import com.fasterxml.jackson.databind.JsonNode;
import ru.smartapp.core.ScenarioContext;

public interface Scenario {

    JsonNode run(ScenarioContext scenarioContext);

}
