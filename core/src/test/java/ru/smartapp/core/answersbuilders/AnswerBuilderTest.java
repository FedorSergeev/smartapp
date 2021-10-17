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
import ru.smartapp.core.common.dto.incoming.MessageToSkillDto;
import ru.smartapp.core.common.dto.outgoing.AnswerToUserDto;
import ru.smartapp.core.common.dto.outgoing.ErrorDto;
import ru.smartapp.core.common.dto.outgoing.NothingFoundDto;
import ru.smartapp.core.common.dto.outgoing.PolicyRunAppDto;

import java.io.IOException;
import java.util.Collections;

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
        MessageToSkillDto incomingMessage = getIncomingMessage();
        SdkAnswerBuilder answerBuilder = sdkAnswerService.getSdkAnswerBuilder();
        answerBuilder.addTexts(Collections.singletonList("Hello from java app"));
        answerBuilder.addVoice("Хелло фром джава апп");
        AnswerToUserDto dto = new AnswerToUserMessageBuilder().build(answerBuilder, incomingMessage);
        assertNotNull(dto);
        assertEquals(MessageName.ANSWER_TO_USER.name(), dto.getMessageName());
        assertEquals(incomingMessage.getMessageId(), dto.getMessageId());
        assertEquals(incomingMessage.getSessionId(), dto.getSessionId());
        assertEquals(incomingMessage.getUuidDTO(), dto.getUuidDTO());
        assertNotNull(dto.getPayload());
        assertNotNull(dto.getPayload().get("device"));
        JsonNode deviceJson = mapper.readTree(mapper.writeValueAsString(incomingMessage.getDeviceDto()));
        MessagesDeSerializationsTest.assertEqualsJsonNodes(deviceJson, dto.getPayload().get("device"));
    }

    @Test
    public void testErrorMessageBuilder() throws IOException {
        MessageToSkillDto incomingMessage = getIncomingMessage();
        ErrorDto dto = new ErrorMessageBuilder().build(-1L, "descr", incomingMessage);
        assertNotNull(dto);
        assertNotNull(dto.getPayload());
        assertEquals(MessageName.ERROR.name(), dto.getMessageName());
        assertEquals(-1L, dto.getPayload().getCode());
        assertEquals("descr", dto.getPayload().getDescription());
        assertEquals(incomingMessage.getMessageId(), dto.getMessageId());
        assertEquals(incomingMessage.getSessionId(), dto.getSessionId());
        assertEquals(incomingMessage.getUuidDTO(), dto.getUuidDTO());
        assertNotNull(dto.getPayload().getDevice());
        assertEquals(incomingMessage.getDeviceDto(), dto.getPayload().getDevice());
    }

    @Test
    public void testNothingFoundMessageBuilder() throws IOException {
        MessageToSkillDto incomingMessage = getIncomingMessage();
        NothingFoundDto dto = new NothingFoundMessageBuilder().build(incomingMessage);
        assertNotNull(dto);
        assertEquals(MessageName.NOTHING_FOUND.name(), dto.getMessageName());
        assertEquals(incomingMessage.getMessageId(), dto.getMessageId());
        assertEquals(incomingMessage.getSessionId(), dto.getSessionId());
        assertEquals(incomingMessage.getUuidDTO(), dto.getUuidDTO());
        assertEquals(incomingMessage.getDeviceDto(), dto.getPayload().getDevice());
    }

    @Test
    public void testPolicyRunAppMessageBuilder() throws IOException {
        MessageToSkillDto incomingMessage = getIncomingMessage();
        JsonNode serverAction = mapper.readTree("{}");
        PolicyRunAppDto dto = new PolicyRunAppMessageBuilder().build("projectName1", serverAction, incomingMessage);
        assertNotNull(dto);
        assertEquals(MessageName.POLICY_RUN_APP.name(), dto.getMessageName());
        assertEquals(incomingMessage.getMessageId(), dto.getMessageId());
        assertEquals(incomingMessage.getSessionId(), dto.getSessionId());
        assertEquals(incomingMessage.getUuidDTO(), dto.getUuidDTO());
        assertNotNull(dto.getPayload());
        assertNotNull(dto.getPayload().getDevice());
        assertEquals("projectName1", dto.getPayload().getProjectName());
        MessagesDeSerializationsTest.assertEqualsJsonNodes(serverAction, dto.getPayload().getServerAction());
        assertEquals(incomingMessage.getDeviceDto(), dto.getPayload().getDevice());
    }

    private MessageToSkillDto getIncomingMessage() throws IOException {
        JsonNode requestJson = mapper.readTree(messageToSkillResource.getInputStream());
        return mapper.readValue(mapper.writeValueAsString(requestJson), MessageToSkillDto.class);

    }
}
