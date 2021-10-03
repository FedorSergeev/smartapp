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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SomeDumbScenarioTest {

    @Value("classpath:messageToSkill.json")
    private Resource messageToSkillResource;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private SomeDumbScenario scenario;

    @Test
    public void test() throws IOException {
        MessageToSkillDTO message = mapper.readValue(messageToSkillResource.getInputStream(), MessageToSkillDTO.class);
        AbstractOutgoingMessage answer = scenario.run(message);
        assertNotNull(answer);
        assertEquals(MessageName.ANSWER_TO_USER.name(), answer.getMessageName());
        assertEquals(message.getSessionId(), answer.getSessionId());
        assertEquals(message.getMessageId(), answer.getMessageId());
        assertEquals(message.getUuidDTO().getSub(), answer.getUuidDTO().getSub());
        assertEquals(message.getUuidDTO().getUserChannel(), answer.getUuidDTO().getUserChannel());
        assertEquals(message.getUuidDTO().getUserId(), answer.getUuidDTO().getUserId());
    }
}
