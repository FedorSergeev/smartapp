package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.smartapp.core.ScenarioExecutor;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDto;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

@Component
public class MessageToSkillHandler extends AbstractMessageHandler {

    private final ScenarioExecutor scenarioExecutor;
    private final ObjectMapper mapper;

    @Autowired
    public MessageToSkillHandler(
            ScenarioExecutor scenarioExecutor,
            ObjectMapper mapper) {
        this.scenarioExecutor = scenarioExecutor;
        this.mapper = mapper;
    }

    public Mono<OutgoingMessage> handle(JsonNode incomingMessage) throws JsonProcessingException {
        return scenarioExecutor.run(buildScenarioContext(incomingMessage));
    }

    @Override
    MessageToSkillDto convert(JsonNode incomingMessage) throws JsonProcessingException {
        return mapper.readValue(mapper.writeValueAsString(incomingMessage), MessageToSkillDto.class);
    }

    private ScenarioContext buildScenarioContext(JsonNode incomingMessage) throws JsonProcessingException {
        MessageToSkillDto dto = convert(incomingMessage);
        return new ScenarioContext(dto.getPayload().getIntent(), dto);
    }
}
