package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import ru.smartapp.core.common.dto.incoming.IncomingMessage;

public abstract class AbstractMessageHandler
        implements MessageHandler {

    abstract IncomingMessage convert(JsonNode incomingMessage) throws JsonProcessingException;
}
