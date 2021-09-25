package ru.smartapp.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.smartapp.core.common.IncomingMessageFactory;
import ru.smartapp.core.common.dto.incoming.IncomingMessage;
import ru.smartapp.core.defaultanswers.NothingFound;
import ru.smartapp.core.intents.Scenario;

import java.util.Optional;

@Service
public class ScenarioExecutor {
    private final Log log = LogFactory.getLog(getClass());
    private ApplicationContext context;
    private ScenariosMap scenarioMap;
    private IncomingMessageFactory incomingMessageFactory;

    @Autowired
    public ScenarioExecutor(
            ApplicationContext context,
            ScenariosMap scenarioMap,
            IncomingMessageFactory incomingMessageFactory
    ) {
        this.scenarioMap = scenarioMap;
        this.incomingMessageFactory = incomingMessageFactory;
        this.context = context;
    }

    /**
     * Method receives incoming message from adapter,
     * get value from field 'intent' and calls corresponding {@link Scenario} by value
     *
     * @param incomingMessage - message received from adapter
     * @return {@link Scenario}'s answer
     */
    public JsonNode run(@Nullable JsonNode incomingMessage) throws JsonProcessingException {
//        TODO: fail fast or fail soft?
        if (!incomingMessageFactory.validateIncomingMessage(incomingMessage)) {
            log.error("Got invalid incoming message");
            return new NothingFound().run();
        }
        IncomingMessage message = incomingMessageFactory.getIncomingMessage(incomingMessage);
        String scenarioId = Optional.ofNullable(incomingMessage)
                .map(info -> info.get("payload"))
                .map(payload -> payload.get("intent"))
                .map(JsonNode::asText)
                .orElse(StringUtils.EMPTY);
        Class<? extends Scenario> scenarioClass = scenarioMap.get(scenarioId);
        if (scenarioClass == null) {
            log.error(String.format("There is no scenario with id %s", scenarioId));
            return new NothingFound().run();
        }
        Scenario scenario = context.getBean(scenarioClass);
        return scenario.run(incomingMessage);
    }
}
