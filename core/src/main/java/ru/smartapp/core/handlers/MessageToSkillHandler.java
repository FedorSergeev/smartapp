package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smartapp.core.ScenarioExecutor;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;

import java.util.Optional;

@Component
public class MessageToSkillHandler<T extends AbstractOutgoingMessage> extends AbstractMessageHandler<MessageToSkillDTO, T> {

    private final ScenarioExecutor scenarioExecutor;
    private final ObjectMapper mapper;

    @Autowired
    public MessageToSkillHandler(ScenarioExecutor scenarioExecutor, ObjectMapper mapper) {
        this.scenarioExecutor = scenarioExecutor;
        this.mapper = mapper;
    }

    public Optional<T> handle(JsonNode incomingMessage) throws JsonProcessingException {
        return Optional.of(scenarioExecutor.run(convert(incomingMessage)));
    }

    @Override
    MessageToSkillDTO convert(JsonNode incomingMessage) throws JsonProcessingException {
        return mapper.readValue(mapper.writeValueAsString(incomingMessage), MessageToSkillDTO.class);
    }
}
