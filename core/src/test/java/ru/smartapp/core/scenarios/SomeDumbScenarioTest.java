package ru.smartapp.core.scenarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.model.User;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

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
        MessageToSkillDTO dto = mapper.readValue(messageToSkillResource.getInputStream(), MessageToSkillDTO.class);
        AbstractOutgoingMessage answer =
                scenario.run(new ScenarioContext<>(new User(dto), dto.getPayload().getIntent(), null, dto, null));
        assertNotNull(answer);
        assertEquals(MessageName.ANSWER_TO_USER.name(), answer.getMessageName());
        assertEquals(dto.getSessionId(), answer.getSessionId());
        assertEquals(dto.getMessageId(), answer.getMessageId());
        assertEquals(dto.getUuidDTO().getSub(), answer.getUuidDTO().getSub());
        assertEquals(dto.getUuidDTO().getUserChannel(), answer.getUuidDTO().getUserChannel());
        assertEquals(dto.getUuidDTO().getUserId(), answer.getUuidDTO().getUserId());
    }
}
