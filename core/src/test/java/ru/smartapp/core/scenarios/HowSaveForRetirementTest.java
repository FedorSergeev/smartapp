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
        MessageToSkillDTO dto = mapper.readValue(messageToSkillResource.getInputStream(), MessageToSkillDTO.class);
        dto.getPayload().setIntent("how_to_save_for_retirement");
        ScenarioContext context = new ScenarioContext(dto.getPayload().getIntent(), dto);
        AbstractOutgoingMessage answer = howSaveForRetirementScenario.run(context).block();
        assertNotNull(answer);
        assertEquals(MessageName.ANSWER_TO_USER.name(), answer.getMessageName());
        assertEquals(dto.getSessionId(), answer.getSessionId());
        assertEquals(dto.getMessageId(), answer.getMessageId());
        assertEquals(dto.getUuidDTO().getSub(), answer.getUuidDTO().getSub());
        assertEquals(dto.getUuidDTO().getUserChannel(), answer.getUuidDTO().getUserChannel());
        assertEquals(dto.getUuidDTO().getUserId(), answer.getUuidDTO().getUserId());
        assertNull(context.getStateId());
        AnswerToUserDTO answerToUserDTO = (AnswerToUserDTO) answer;
        assertTrue(answerToUserDTO.getPayload().get("finished").asBoolean());
        String pronounceText = answerToUserDTO.getPayload().get("pronounceText").asText();
        assertNotNull(pronounceText);
        assertTrue(pronounceText.length() > 0);
        String text = answerToUserDTO.getPayload().withArray("items").get(0).get("bubble").get("text").asText();
        assertNotNull(text);
        assertTrue(text.length() > 0);
    }
}
