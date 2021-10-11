package ru.smartapp.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.smartapp.core.answersbuilders.NothingFoundMessageBuilder;
import ru.smartapp.core.cache.CacheAdapter;
import ru.smartapp.core.common.dao.UserScenarioDAO;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.dto.outgoing.AnswerToUserDTO;
import ru.smartapp.core.common.model.ScenarioContext;
import ru.smartapp.core.scenarios.Scenario;

import java.util.Optional;

@Service
public class ScenarioExecutor {
    private final Log log = LogFactory.getLog(getClass());
    private ApplicationContext context;
    private ScenariosMap scenarioMap;
    private CacheAdapter cacheAdapter;

    @Autowired
    public ScenarioExecutor(
            ApplicationContext context,
            ScenariosMap scenarioMap,
            CacheAdapter cacheAdapter
    ) {
        this.scenarioMap = scenarioMap;
        this.context = context;
        this.cacheAdapter = cacheAdapter;
    }

    /**
     * Method receives incoming message from adapter,
     * get value from field 'intent' and calls corresponding {@link Scenario} by value
     *
     * @param scenarioContext - context used to start and execute {@link Scenario}
     * @return {@link Scenario}'s answer
     */
    @NotNull
    public <INCOMING extends AbstractIncomingMessage>
    AbstractOutgoingMessage run(ScenarioContext<INCOMING> scenarioContext) throws JsonProcessingException {
        String intent = scenarioContext.getIntent();
        Class<? extends Scenario> scenarioClass = scenarioMap.get(intent);
        if (scenarioClass == null) {
            log.error(String.format("There is no scenario with id %s", intent));
            return new NothingFoundMessageBuilder().build(scenarioContext.getMessage());
        }
        Scenario scenario = context.getBean(scenarioClass);
        AbstractOutgoingMessage outgoingMessage = scenario.run(scenarioContext);
        if (isFinished(outgoingMessage)) {
            cacheAdapter.deleteUserScenario(scenarioContext.getUser().getUserUniqueId());
        } else {
            UserScenarioDAO userScenarioDAO = new UserScenarioDAO();
            userScenarioDAO.setUserUniqueId(scenarioContext.getUser().getUserUniqueId());
            userScenarioDAO.setIntent(scenarioContext.getIntent());
            userScenarioDAO.setStateId(scenarioContext.getStateId());
            userScenarioDAO.setScenarioData(scenarioContext.getScenarioData());
            cacheAdapter.updateUserScenario(userScenarioDAO);
        }
        return outgoingMessage;
    }

    private <OUTGOING extends AbstractOutgoingMessage> boolean isFinished(OUTGOING outgoingMessage) {
        if (outgoingMessage instanceof AnswerToUserDTO) {
            AnswerToUserDTO answerToUserDTO = (AnswerToUserDTO) outgoingMessage;
            return Optional.ofNullable(answerToUserDTO)
                    .map(AnswerToUserDTO::getPayload)
                    .filter(json -> json.hasNonNull("finished"))
                    .map(json -> json.get("finished").asBoolean())
                    .orElse(true);
        }
        return false;
    }
}
