package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;

public abstract class AbstractMessageHandler<INCOMING extends AbstractIncomingMessage, OUTGOING extends AbstractOutgoingMessage>
        implements MessageHandler<INCOMING, OUTGOING> {

    abstract INCOMING convert(JsonNode incomingMessage) throws JsonProcessingException;
}
