package ru.smartapp.core.scenarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

public interface Scenario {

    Mono<AbstractOutgoingMessage> run(ScenarioContext<? extends AbstractIncomingMessage> context) throws JsonProcessingException;

}
