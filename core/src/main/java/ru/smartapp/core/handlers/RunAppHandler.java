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
import ru.smartapp.core.common.dto.incoming.RunAppDTO;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

@Slf4j
@Component
public class RunAppHandler<I extends RunAppDTO> extends AbstractMessageHandler<I> {
    private ScenarioExecutor scenarioExecutor;
    private ObjectMapper mapper;
    private CacheAdapter cacheAdapter;

    @Autowired
    public RunAppHandler(ObjectMapper mapper, ScenarioExecutor scenarioExecutor, CacheAdapter cacheAdapter) {
        this.mapper = mapper;
        this.scenarioExecutor = scenarioExecutor;
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

    private ScenarioContext buildScenarioContext(JsonNode incomingMessage) throws JsonProcessingException {
        I dto = convert(incomingMessage);
        return new ScenarioContext(dto.getPayload().getIntent(), dto);
    }
}
