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
public class HowSaveForRetirementTest {

    @Value("classpath:messageToSkill.json")
    private Resource messageToSkillResource;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private HowSaveForRetirementScenario howSaveForRetirementScenario;

    @Test
    public void testMessage() throws IOException {
        MessageToSkillDto dto = mapper.readValue(messageToSkillResource.getInputStream(), MessageToSkillDto.class);
        dto.getPayload().setIntent("how_to_save_for_retirement");
        ScenarioContext context = new ScenarioContext(dto.getPayload().getIntent(), dto);
        OutgoingMessage answer = howSaveForRetirementScenario.run(context).block();
        assertNotNull(answer);
        assertTrue((answer instanceof AbstractOutgoingMessage));
        AbstractOutgoingMessage abstractOutgoingMessage = (AbstractOutgoingMessage) answer;
        assertEquals(MessageName.ANSWER_TO_USER.name(), abstractOutgoingMessage.getMessageName());
        assertEquals(dto.getSessionId(), abstractOutgoingMessage.getSessionId());
        assertEquals(dto.getMessageId(), abstractOutgoingMessage.getMessageId());
        assertEquals(dto.getUuidDTO().getSub(), abstractOutgoingMessage.getUuidDTO().getSub());
        assertEquals(dto.getUuidDTO().getUserChannel(), abstractOutgoingMessage.getUuidDTO().getUserChannel());
        assertEquals(dto.getUuidDTO().getUserId(), abstractOutgoingMessage.getUuidDTO().getUserId());
        assertNull(context.getStateId());
        AnswerToUserDto answerToUserDTO = (AnswerToUserDto) abstractOutgoingMessage;
        assertTrue(answerToUserDTO.getPayload().get("finished").asBoolean());
        String pronounceText = answerToUserDTO.getPayload().get("pronounceText").asText();
        assertNotNull(pronounceText);
        assertTrue(pronounceText.length() > 0);
        String text = answerToUserDTO.getPayload().withArray("items").get(0).get("bubble").get("text").asText();
        assertNotNull(text);
        assertTrue(text.length() > 0);
    }
}
