package ru.smartapp.core.scenarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

public interface Scenario {

    Mono<OutgoingMessage> run(ScenarioContext context) throws JsonProcessingException;

}
