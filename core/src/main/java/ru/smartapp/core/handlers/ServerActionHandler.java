package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.smartapp.core.ScenarioExecutor;
import ru.smartapp.core.common.dto.incoming.ServerActionDto;
import ru.smartapp.core.common.dto.incoming.ServerActionPayloadDto;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

import java.util.Optional;

@Slf4j
@Component
public class ServerActionHandler extends AbstractMessageHandler {
    private final ScenarioExecutor scenarioExecutor;
    private final ObjectMapper mapper;

    @Autowired
    public ServerActionHandler(ObjectMapper mapper, ScenarioExecutor scenarioExecutor) {
        this.scenarioExecutor = scenarioExecutor;
        this.mapper = mapper;
    }

    public Mono<OutgoingMessage> handle(JsonNode incomingMessage) throws JsonProcessingException {
        return scenarioExecutor.run(buildScenarioContext(incomingMessage));
    }

    @Override
    ServerActionDto convert(JsonNode incomingMessage) throws JsonProcessingException {
        return mapper.readValue(mapper.writeValueAsString(incomingMessage), ServerActionDto.class);
    }

    private ScenarioContext buildScenarioContext(JsonNode incomingMessage) throws JsonProcessingException {
        ServerActionDto dto = convert(incomingMessage);
        // TODO откуда брать айди сценария
        String intent = Optional.ofNullable(dto.getPayload())
                .map(ServerActionPayloadDto::getServerAction)
                .filter(jsonNode -> jsonNode.hasNonNull("intent"))
                .map(jsonNode -> jsonNode.get("intent"))
                .map(JsonNode::asText)
                .orElse(null);
        return new ScenarioContext(intent, dto);
    }
}
