package ru.smartapp.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.incoming.MessageToSkillPayloadDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.defaultanswers.NothingFoundMessageBuilder;
import ru.smartapp.core.scenarios.Scenario;

import java.util.Optional;

@Service
public class ScenarioExecutor {
    private final Log log = LogFactory.getLog(getClass());
    private final ApplicationContext context;
    private final ScenariosMap scenarioMap;

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
     * @param incomingMessage - message received from adapter
     * @return {@link Scenario}'s answer
     */
    // TODO: нужно определи модели данных, с которыми будет работать сценарный экзекутор.
    //  Чтоб хендлить messageToSkill, runApp, serverAction
    //  Да и не только сообщения из кафки, но и из реста, например
    public <INCOMING extends AbstractIncomingMessage, OUTGOING extends AbstractOutgoingMessage> OUTGOING
    run(INCOMING incomingMessage) throws JsonProcessingException {
        // TODO
        MessageToSkillDTO messageToSkillDTO = (MessageToSkillDTO) incomingMessage;
        String scenarioId = Optional.ofNullable(messageToSkillDTO)
                .map(MessageToSkillDTO::getPayload)
                .map(MessageToSkillPayloadDTO::getIntent)
                .orElse(StringUtils.EMPTY);

        Class<? extends Scenario> scenarioClass = scenarioMap.get(scenarioId);
        if (scenarioClass == null) {
            log.error(String.format("There is no scenario with id %s", scenarioId));
            // TODO
            return (OUTGOING) new NothingFoundMessageBuilder().run();
        }
        Scenario scenario = context.getBean(scenarioClass);
        return scenario.run(incomingMessage);
    }
}
