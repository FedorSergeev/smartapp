package ru.smartapp.core.scenarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDto;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.dto.outgoing.AnswerToUserDto;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

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
        MessageToSkillDto dto = mapper.readValue(messageToSkillResource.getInputStream(), MessageToSkillDto.class);
        ScenarioContext context = new ScenarioContext(dto.getPayload().getIntent(), dto);
        OutgoingMessage answer = someDumbScenario.run(context).block();
        assertNotNull(answer);
        assertTrue((answer instanceof AbstractOutgoingMessage));
        AbstractOutgoingMessage abstractOutgoingMessage = (AbstractOutgoingMessage) answer;
        assertEquals(MessageName.ANSWER_TO_USER.name(), abstractOutgoingMessage.getMessageName());
        assertEquals(dto.getSessionId(), abstractOutgoingMessage.getSessionId());
        assertEquals(dto.getMessageId(), abstractOutgoingMessage.getMessageId());
        assertEquals(dto.getUuidDTO().getSub(), abstractOutgoingMessage.getUuidDTO().getSub());
        assertEquals(dto.getUuidDTO().getUserChannel(), abstractOutgoingMessage.getUuidDTO().getUserChannel());
        assertEquals(dto.getUuidDTO().getUserId(), abstractOutgoingMessage.getUuidDTO().getUserId());
        assertEquals("bye", context.getStateId());
        AnswerToUserDto answerToUserDTO = (AnswerToUserDto) abstractOutgoingMessage;
        assertFalse(answerToUserDTO.getPayload().get("finished").asBoolean());
    }

    @Test
    public void testBye() throws IOException {
        MessageToSkillDto dto = mapper.readValue(messageToSkillResource.getInputStream(), MessageToSkillDto.class);
        ScenarioContext context = new ScenarioContext(dto.getPayload().getIntent(), dto);
        OutgoingMessage answer1 = someDumbScenario.run(context).block();
        context.setMessage(dto);
        OutgoingMessage answer2 = someDumbScenario.run(context).block();
        assertNotNull(answer2);
        assertTrue((answer2 instanceof AbstractOutgoingMessage));
        AbstractOutgoingMessage abstractOutgoingMessage2 = (AbstractOutgoingMessage) answer2;
        assertEquals(MessageName.ANSWER_TO_USER.name(), abstractOutgoingMessage2.getMessageName());
        assertEquals(dto.getSessionId(), abstractOutgoingMessage2.getSessionId());
        assertEquals(dto.getMessageId(), abstractOutgoingMessage2.getMessageId());
        assertEquals(dto.getUuidDTO().getSub(), abstractOutgoingMessage2.getUuidDTO().getSub());
        assertEquals(dto.getUuidDTO().getUserChannel(), abstractOutgoingMessage2.getUuidDTO().getUserChannel());
        assertEquals(dto.getUuidDTO().getUserId(), abstractOutgoingMessage2.getUuidDTO().getUserId());
        AnswerToUserDto answerToUserDTO = (AnswerToUserDto) abstractOutgoingMessage2;
        assertTrue(answerToUserDTO.getPayload().get("finished").asBoolean());
    }
}
