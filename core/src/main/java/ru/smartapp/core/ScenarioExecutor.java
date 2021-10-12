package ru.smartapp.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.smartapp.core.answersbuilders.NothingFoundMessageBuilder;
import ru.smartapp.core.cache.CacheAdapter;
import ru.smartapp.core.common.dao.UserScenarioDAO;
import ru.smartapp.core.common.dto.outgoing.AnswerToUserDTO;
import ru.smartapp.core.common.dto.outgoing.NothingFoundDTO;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;
import ru.smartapp.core.scenarios.Scenario;

import java.util.Optional;

@Slf4j
@Service
public class ScenarioExecutor {
    private final ApplicationContext context;
    private final ScenariosMap scenarioMap;
    private final CacheAdapter cacheAdapter;

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
    public Mono<OutgoingMessage> run(ScenarioContext scenarioContext) throws JsonProcessingException {
        String intent = scenarioContext.getIntent();
        Class<? extends Scenario> scenarioClass = scenarioMap.get(intent);
        if (scenarioClass == null) {
            log.error(String.format("There is no scenario with id %s", intent));
            NothingFoundDTO nothingFoundDTO = new NothingFoundMessageBuilder().build(scenarioContext.getMessage());
            return Mono.just(nothingFoundDTO);
        }
        Scenario scenario = context.getBean(scenarioClass);
        return scenario.run(scenarioContext)
                .doOnNext(outgoingMessage -> updateCache(scenarioContext, outgoingMessage))
                .map(outgoingMessage -> outgoingMessage);
    }

    private void updateCache(ScenarioContext scenarioContext, OutgoingMessage outgoingMessage) {
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
    }

    private boolean isFinished(OutgoingMessage outgoingMessage) {
        if (outgoingMessage instanceof AnswerToUserDTO) {
            AnswerToUserDTO answerToUserDTO = (AnswerToUserDTO) outgoingMessage;
            return Optional.of(answerToUserDTO)
                    .map(AnswerToUserDTO::getPayload)
                    .filter(json -> json.hasNonNull("finished"))
                    .map(json -> json.get("finished").asBoolean())
                    .orElse(true);
        }
        return false;
    }
}
