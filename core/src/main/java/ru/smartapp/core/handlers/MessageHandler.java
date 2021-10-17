package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;

@FunctionalInterface
public interface MessageHandler {

    Mono<OutgoingMessage> handle(JsonNode incomingMessage) throws JsonProcessingException;

}
