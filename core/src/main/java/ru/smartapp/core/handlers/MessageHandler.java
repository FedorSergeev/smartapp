package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;

import java.util.Optional;

@FunctionalInterface
public interface MessageHandler<INCOMING extends AbstractIncomingMessage> {

    Optional<AbstractOutgoingMessage> handle(JsonNode incomingMessage) throws JsonProcessingException;

}
