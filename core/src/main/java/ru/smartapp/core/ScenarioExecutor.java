package ru.smartapp.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.smartapp.core.answersbuilders.NothingFoundMessageBuilder;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;
import ru.smartapp.core.scenarios.Scenario;

@Service
public class ScenarioExecutor {
    private final Log log = LogFactory.getLog(getClass());
    private ApplicationContext context;
    private ScenariosMap scenarioMap;

    @Autowired
    public ScenarioExecutor(
            ApplicationContext context,
            ScenariosMap scenarioMap
    ) {
        this.scenarioMap = scenarioMap;
        this.context = context;
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
        // TODO: check new_session field
        String intent = scenarioContext.getIntent();
        Class<? extends Scenario> scenarioClass = scenarioMap.get(intent);
        if (scenarioClass == null) {
            log.error(String.format("There is no scenario with id %s", intent));
            return new NothingFoundMessageBuilder().build();
        }
        Scenario scenario = context.getBean(scenarioClass);
        // TODO: check finished field
        return scenario.run(scenarioContext);
    }
}
