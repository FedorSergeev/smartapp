package ru.smartapp.core.defaultanswers;

import com.fasterxml.jackson.databind.JsonNode;

public class NothingFound implements Answer{
    @Override
    public JsonNode run() {
//        TODO implement
        return null;
    }
}
