package ru.smartapp.core.defaultanswers;

import com.fasterxml.jackson.databind.JsonNode;

public interface Answer {
    JsonNode run();
}
