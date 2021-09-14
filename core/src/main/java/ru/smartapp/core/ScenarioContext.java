package ru.smartapp.core;

import com.fasterxml.jackson.databind.JsonNode;

public class ScenarioContext {
    private String intent;
    private String stateId;
    private JsonNode message;

    public ScenarioContext(JsonNode dbInfo) {
        //TODO: build context from db info
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public JsonNode getMessage() {
        return message;
    }

    public void setMessage(JsonNode message) {
        this.message = message;
    }
}
