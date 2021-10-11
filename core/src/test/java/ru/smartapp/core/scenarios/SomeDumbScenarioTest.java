package ru.smartapp.core.scenarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.dto.outgoing.AnswerToUserDTO;
import ru.smartapp.core.common.model.ScenarioContext;
import ru.smartapp.core.handlers.IncomingMessageRouter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SomeDumbScenarioTest {

    @Value("classpath:messageToSkill.json")
    private Resource messageToSkillResource;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private SomeDumbScenario someDumbScenario;

    @Test
    public void testHello() throws IOException {
        MessageToSkillDTO dto = mapper.readValue(messageToSkillResource.getInputStream(), MessageToSkillDTO.class);
        ScenarioContext<MessageToSkillDTO> context = new ScenarioContext<>(dto.getPayload().getIntent(), dto);
        AbstractOutgoingMessage answer = someDumbScenario.run(context);
        assertNotNull(answer);
        assertEquals(MessageName.ANSWER_TO_USER.name(), answer.getMessageName());
        assertEquals(dto.getSessionId(), answer.getSessionId());
        assertEquals(dto.getMessageId(), answer.getMessageId());
        assertEquals(dto.getUuidDTO().getSub(), answer.getUuidDTO().getSub());
        assertEquals(dto.getUuidDTO().getUserChannel(), answer.getUuidDTO().getUserChannel());
        assertEquals(dto.getUuidDTO().getUserId(), answer.getUuidDTO().getUserId());
        assertEquals("bye", context.getStateId());
        AnswerToUserDTO answerToUserDTO = (AnswerToUserDTO) answer;
        assertFalse(answerToUserDTO.getPayload().get("finished").asBoolean());
    }

    @Test
    public void testBye() throws IOException {
        MessageToSkillDTO dto = mapper.readValue(messageToSkillResource.getInputStream(), MessageToSkillDTO.class);
        ScenarioContext<MessageToSkillDTO> context = new ScenarioContext<>(dto.getPayload().getIntent(), dto);
        AbstractOutgoingMessage answer1 = someDumbScenario.run(context);
        context.setMessage(dto);
        AbstractOutgoingMessage answer2 = someDumbScenario.run(context);
        assertNotNull(answer2);
        assertEquals(MessageName.ANSWER_TO_USER.name(), answer2.getMessageName());
        assertEquals(dto.getSessionId(), answer2.getSessionId());
        assertEquals(dto.getMessageId(), answer2.getMessageId());
        assertEquals(dto.getUuidDTO().getSub(), answer2.getUuidDTO().getSub());
        assertEquals(dto.getUuidDTO().getUserChannel(), answer2.getUuidDTO().getUserChannel());
        assertEquals(dto.getUuidDTO().getUserId(), answer2.getUuidDTO().getUserId());
        AnswerToUserDTO answerToUserDTO = (AnswerToUserDTO) answer2;
        assertTrue(answerToUserDTO.getPayload().get("finished").asBoolean());
    }
}
