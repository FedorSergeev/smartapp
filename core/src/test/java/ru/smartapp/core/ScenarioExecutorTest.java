package ru.smartapp.core;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ScenarioExecutorTest {

    @Value("classpath:messageToSkill.json")
    private Resource messageToSkillResource;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ScenarioExecutor scenarioExecutor;
    @Autowired
    private ScenariosMap scenariosMap;

    // TODO: доделать ассерты
    @Test
    public void testSomeDumbScenario() throws IOException {
        MessageToSkillDTO message1 = getMessage();
        ScenarioContext<MessageToSkillDTO> scenarioContext1 = new ScenarioContext<>(message1.getPayload().getIntent(), message1);
        AbstractOutgoingMessage answer1 = scenarioExecutor.run(scenarioContext1);

        JsonNode response1 = mapper.readTree(mapper.writeValueAsString(answer1));
        assertNotNull(response1);

        MessageToSkillDTO message2 = getMessage();
        message2.getPayload().setNewSession(false);
        ScenarioContext<MessageToSkillDTO> scenarioContext2 = new ScenarioContext<>(message2.getPayload().getIntent(), message2);
        AbstractOutgoingMessage answer2 = scenarioExecutor.run(scenarioContext2);

        JsonNode response2 = mapper.readTree(mapper.writeValueAsString(answer2));
        assertNotNull(response2);
    }

    @Test
    public void test() throws IOException {
        MessageToSkillDTO message = getMessage();
        AbstractOutgoingMessage answer =
                scenarioExecutor.run(new ScenarioContext<>(message.getPayload().getIntent(), message));
        JsonNode response = mapper.readTree(mapper.writeValueAsString(answer));
        assertNotNull(response);
    }

    private MessageToSkillDTO getMessage() throws IOException {
        JsonNode requestJson = mapper.readTree(messageToSkillResource.getInputStream());
        return mapper.readValue(mapper.writeValueAsString(requestJson), MessageToSkillDTO.class);
    }
}
