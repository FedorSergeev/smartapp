package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smartapp.core.ScenarioExecutor;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;

@Component
public class MessageToSkillHandler<T extends AbstractOutgoingMessage> implements MessageHandler<MessageToSkillDTO, T> {

    private ScenarioExecutor scenarioExecutor;
    private ObjectMapper mapper;

    @Autowired
    public MessageToSkillHandler(ScenarioExecutor scenarioExecutor, ObjectMapper mapper) {
        this.scenarioExecutor = scenarioExecutor;
        this.mapper = mapper;
    }

    public T handle(MessageToSkillDTO incoming) throws JsonProcessingException {
        return scenarioExecutor.run(incoming);
    }

}
