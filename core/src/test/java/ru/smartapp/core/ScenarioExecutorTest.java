package ru.smartapp.core;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;
import ru.smartapp.core.common.model.ScenarioContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testSomeDumbScenario() throws IOException {
        MessageToSkillDTO message = getMessage();
        ScenarioContext scenarioContext = new ScenarioContext(message.getPayload().getIntent(), message);
        OutgoingMessage answer = scenarioExecutor.run(scenarioContext).block();

        JsonNode response = mapper.readTree(mapper.writeValueAsString(answer));
        assertNotNull(response);
        assertFalse(response.get("payload").get("finished").asBoolean());
        assertTrue(response.toString().contains("Хелло"));

        message = getMessage();
        message.getPayload().setNewSession(false);
        scenarioContext = new ScenarioContext(message.getPayload().getIntent(), message);
        answer = scenarioExecutor.run(scenarioContext).block();

        response = mapper.readTree(mapper.writeValueAsString(answer));
        assertNotNull(response);
        assertTrue(response.get("payload").get("finished").asBoolean());
        assertTrue(response.toString().contains("Упс"));
    }

    @Test
    public void test() throws IOException {
        MessageToSkillDTO message = getMessage();
        OutgoingMessage answer =
                scenarioExecutor.run(new ScenarioContext(message.getPayload().getIntent(), message)).block();
        JsonNode response = mapper.readTree(mapper.writeValueAsString(answer));
        assertNotNull(response);
    }

    private MessageToSkillDTO getMessage() throws IOException {
        JsonNode requestJson = mapper.readTree(messageToSkillResource.getInputStream());
        return mapper.readValue(mapper.writeValueAsString(requestJson), MessageToSkillDTO.class);
    }
}
