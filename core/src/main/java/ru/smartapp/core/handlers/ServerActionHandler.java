package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.smartapp.core.ScenarioExecutor;
import ru.smartapp.core.cache.CacheAdapter;
import ru.smartapp.core.common.dto.incoming.ServerActionDTO;
import ru.smartapp.core.common.dto.incoming.ServerActionPayloadDTO;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

import java.util.Optional;

@Slf4j
@Component
public class ServerActionHandler<I extends ServerActionDTO> extends AbstractMessageHandler<I> {
    private ScenarioExecutor scenarioExecutor;
    private ObjectMapper mapper;
    private CacheAdapter cacheAdapter;

    @Autowired
    public ServerActionHandler(ObjectMapper mapper, ScenarioExecutor scenarioExecutor, CacheAdapter cacheAdapter) {
        this.scenarioExecutor = scenarioExecutor;
        this.mapper = mapper;
        this.cacheAdapter = cacheAdapter;
    }

    public Mono<OutgoingMessage> handle(JsonNode incomingMessage) throws JsonProcessingException {
        return scenarioExecutor.run(buildScenarioContext(incomingMessage));
    }

    @Override
    I convert(JsonNode incomingMessage) throws JsonProcessingException {
        return mapper.readValue(mapper.writeValueAsString(incomingMessage), new TypeReference<I>() {
        });
    }

    private ScenarioContext<I> buildScenarioContext(JsonNode incomingMessage) throws JsonProcessingException {
        I dto = convert(incomingMessage);
        // TODO откуда брать айди сценария
        String intent = Optional.ofNullable(dto.getPayload())
                .map(ServerActionPayloadDTO::getServerAction)
                .filter(jsonNode -> jsonNode.hasNonNull("intent"))
                .map(jsonNode -> jsonNode.get("intent"))
                .map(JsonNode::asText)
                .orElse(null);
        return new ScenarioContext<>(intent, dto);
    }
}
