package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smartapp.core.ScenarioExecutor;
import ru.smartapp.core.cache.CacheAdapter;
import ru.smartapp.core.common.dao.ScenarioDataDAO;
import ru.smartapp.core.common.dao.UserScenarioDAO;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;
import ru.smartapp.core.common.model.User;

import java.util.Optional;

@Component
public class MessageToSkillHandler<I extends MessageToSkillDTO> extends AbstractMessageHandler<I> {

    private ScenarioExecutor scenarioExecutor;
    private ObjectMapper mapper;
    private CacheAdapter cacheAdapter;

    @Autowired
    public MessageToSkillHandler(
            ScenarioExecutor scenarioExecutor,
            ObjectMapper mapper,
            CacheAdapter cacheAdapter) {
        this.scenarioExecutor = scenarioExecutor;
        this.mapper = mapper;
        this.cacheAdapter = cacheAdapter;
    }

    public Optional<AbstractOutgoingMessage> handle(JsonNode incomingMessage) throws JsonProcessingException {
        return Optional.of(scenarioExecutor.run(buildScenarioContext(incomingMessage)));
    }

    @Override
    I convert(JsonNode incomingMessage) throws JsonProcessingException {
        return mapper.readValue(mapper.writeValueAsString(incomingMessage), new TypeReference<I>() {
        });
    }

    private ScenarioContext<I> buildScenarioContext(JsonNode incomingMessage) throws JsonProcessingException {
        I dto = convert(incomingMessage);
        // TODO повтор кода
        User user = new User(dto);
        Optional<UserScenarioDAO> userScenarioOptional = cacheAdapter.getUserScenario(user.getUserUniqueId());
        String stateId = userScenarioOptional.map(UserScenarioDAO::getStateId).orElse(null);
        ScenarioDataDAO scenarioData = userScenarioOptional.map(UserScenarioDAO::getScenarioData).orElse(null);
        return new ScenarioContext<>(user, dto.getPayload().getIntent(), stateId, dto, scenarioData);
    }
}