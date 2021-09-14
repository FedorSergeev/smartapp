package ru.smartapp.core.intents;

import com.fasterxml.jackson.databind.JsonNode;

public interface Scenario {

    JsonNode run(JsonNode incomingMessage);

}
