package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;

@FunctionalInterface
public interface MessageHandler<INCOMING extends AbstractIncomingMessage, OUTGOING extends AbstractOutgoingMessage> {

    OUTGOING handle(INCOMING incoming) throws JsonProcessingException;

}
