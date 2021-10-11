package ru.smartapp.core.scenarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

public interface Scenario {

    AbstractOutgoingMessage run(ScenarioContext<? extends AbstractIncomingMessage> context) throws JsonProcessingException;

}
