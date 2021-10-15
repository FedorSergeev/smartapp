package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.smartapp.core.ScenarioExecutor;
import ru.smartapp.core.common.dto.incoming.RunAppDto;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

@Slf4j
@Component
public class RunAppHandler extends AbstractMessageHandler {
    private final ScenarioExecutor scenarioExecutor;
    private final ObjectMapper mapper;

    @Autowired
    public RunAppHandler(ObjectMapper mapper, ScenarioExecutor scenarioExecutor) {
        this.mapper = mapper;
        this.scenarioExecutor = scenarioExecutor;
    }

    public Mono<OutgoingMessage> handle(JsonNode incomingMessage) throws JsonProcessingException {
        return scenarioExecutor.run(buildScenarioContext(incomingMessage));
    }

    @Override
    RunAppDto convert(JsonNode incomingMessage) throws JsonProcessingException {
        return mapper.readValue(mapper.writeValueAsString(incomingMessage), RunAppDto.class);
    }

    private ScenarioContext buildScenarioContext(JsonNode incomingMessage) throws JsonProcessingException {
        RunAppDto dto = convert(incomingMessage);
        return new ScenarioContext(dto.getPayload().getIntent(), dto);
    }
}
