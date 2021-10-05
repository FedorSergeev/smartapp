package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smartapp.core.ScenarioExecutor;
import ru.smartapp.core.cache.CacheAdapter;
import ru.smartapp.core.common.dao.ScenarioDataDAO;
import ru.smartapp.core.common.dao.UserScenarioDAO;
import ru.smartapp.core.common.dto.incoming.ServerActionDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;
import ru.smartapp.core.common.model.User;

import java.util.Optional;

@Component
public class ServerActionHandler<I extends ServerActionDTO> extends AbstractMessageHandler<I> {
    private final Log log = LogFactory.getLog(getClass());
    private ScenarioExecutor scenarioExecutor;
    private ObjectMapper mapper;
    private CacheAdapter cacheAdapter;

    @Autowired
    public ServerActionHandler(ObjectMapper mapper, ScenarioExecutor scenarioExecutor, CacheAdapter cacheAdapter) {
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
        // TODO наложить ограничение на объект ServerActionDTO: требовать непустое поле текстовое intent в  server_actions
        String intent = null;
        if (dto.getPayload().getServerAction().hasNonNull("intent")) {
            intent = dto.getPayload().getServerAction().get("intent").asText();
        }
        return new ScenarioContext<>(user, intent, stateId, dto, scenarioData);
    }
}
