package ru.smartapp.core;

import com.fasterxml.jackson.databind.JsonNode;

public class ScenarioContext {
    private String intent;
    private String stateId;

    public ScenarioContext(JsonNode dbInfo) {
        //TODO: build context from db info
    }
}
