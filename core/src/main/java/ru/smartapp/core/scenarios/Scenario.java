package ru.smartapp.core.scenarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;

public interface Scenario {

    <T extends AbstractOutgoingMessage> T run(AbstractIncomingMessage incomingMessage) throws JsonProcessingException;

}
