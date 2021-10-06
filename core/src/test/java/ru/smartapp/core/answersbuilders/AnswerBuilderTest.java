package ru.smartapp.core.answersbuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ru.smartapp.core.answersbuilders.sdkanswerbuilder.SdkAnswerBuilder;
import ru.smartapp.core.answersbuilders.sdkanswerbuilder.SdkAnswerService;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.MessagesDeSerializationsTest;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.outgoing.AnswerToUserDTO;
import ru.smartapp.core.common.dto.outgoing.ErrorDTO;
import ru.smartapp.core.common.dto.outgoing.NothingFoundDTO;
import ru.smartapp.core.common.dto.outgoing.PolicyRunAppDTO;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AnswerBuilderTest {
    @Autowired
    private SdkAnswerService sdkAnswerService;
    @Autowired
    private ObjectMapper mapper;
    @Value("classpath:messageToSkill.json")
    private Resource messageToSkillResource;

    @Test
    public void testAnswerToUserMessageBuilder() throws IOException {
        MessageToSkillDTO incomingMessage = getIncomingMessage();
        SdkAnswerBuilder answerBuilder = sdkAnswerService.getSdkAnswerBuilder();
        answerBuilder.addText("Hello from java app");
        answerBuilder.addVoice("Хелло фром джава апп");
        AnswerToUserDTO dto = new AnswerToUserMessageBuilder().build(answerBuilder, incomingMessage);
        assertNotNull(dto);
        assertEquals(MessageName.ANSWER_TO_USER.name(), dto.getMessageName());
        assertEquals(incomingMessage.getMessageId(), dto.getMessageId());
        assertEquals(incomingMessage.getSessionId(), dto.getSessionId());
        assertEquals(incomingMessage.getUuidDTO(), dto.getUuidDTO());
        assertNotNull(dto.getPayload());
        assertNotNull(dto.getPayload().get("device"));
        JsonNode deviceJson = mapper.readTree(mapper.writeValueAsString(incomingMessage.getDevice()));
        MessagesDeSerializationsTest.assertEqualsJsonNodes(deviceJson, dto.getPayload().get("device"));
    }

    @Test
    public void testErrorMessageBuilder() throws IOException {
        MessageToSkillDTO incomingMessage = getIncomingMessage();
        ErrorDTO dto = new ErrorMessageBuilder().build(-1L, "descr", incomingMessage);
        assertNotNull(dto);
        assertNotNull(dto.getPayload());
        assertEquals(MessageName.ERROR.name(), dto.getMessageName());
        assertEquals(-1L, dto.getPayload().getCode());
        assertEquals("descr", dto.getPayload().getDescription());
        assertEquals(incomingMessage.getMessageId(), dto.getMessageId());
        assertEquals(incomingMessage.getSessionId(), dto.getSessionId());
        assertEquals(incomingMessage.getUuidDTO(), dto.getUuidDTO());
        assertNotNull(dto.getPayload().getDevice());
        assertEquals(incomingMessage.getDevice(), dto.getPayload().getDevice());
    }

    @Test
    public void testNothingFoundMessageBuilder() throws IOException {
        MessageToSkillDTO incomingMessage = getIncomingMessage();
        NothingFoundDTO dto = new NothingFoundMessageBuilder().build(incomingMessage);
        assertNotNull(dto);
        assertEquals(MessageName.NOTHING_FOUND.name(), dto.getMessageName());
        assertEquals(incomingMessage.getMessageId(), dto.getMessageId());
        assertEquals(incomingMessage.getSessionId(), dto.getSessionId());
        assertEquals(incomingMessage.getUuidDTO(), dto.getUuidDTO());
        assertEquals(incomingMessage.getDevice(), dto.getPayload().getDevice());
    }

    @Test
    public void testPolicyRunAppMessageBuilder() throws IOException {
        MessageToSkillDTO incomingMessage = getIncomingMessage();
        JsonNode serverAction = mapper.readTree("{}");
        PolicyRunAppDTO dto = new PolicyRunAppMessageBuilder().build("projectName1", serverAction, incomingMessage);
        assertNotNull(dto);
        assertEquals(MessageName.POLICY_RUN_APP.name(), dto.getMessageName());
        assertEquals(incomingMessage.getMessageId(), dto.getMessageId());
        assertEquals(incomingMessage.getSessionId(), dto.getSessionId());
        assertEquals(incomingMessage.getUuidDTO(), dto.getUuidDTO());
        assertNotNull(dto.getPayload());
        assertNotNull(dto.getPayload().getDevice());
        assertEquals("projectName1", dto.getPayload().getProjectName());
        MessagesDeSerializationsTest.assertEqualsJsonNodes(serverAction, dto.getPayload().getServerAction());
        assertEquals(incomingMessage.getDevice(), dto.getPayload().getDevice());
    }

    private MessageToSkillDTO getIncomingMessage() throws IOException {
        JsonNode requestJson = mapper.readTree(messageToSkillResource.getInputStream());
        return mapper.readValue(mapper.writeValueAsString(requestJson), MessageToSkillDTO.class);

    }
}
