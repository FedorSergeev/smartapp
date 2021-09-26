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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ScenarioExecutorTest {

    @Value("classpath:messageToSkill.json")
    private Resource requestResource;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ScenarioExecutor scenarioExecutor;
    @Autowired
    private ScenariosMap scenariosMap;

    @Test
    public void test() throws IOException {
        assertNotNull(requestResource);
        JsonNode requestJson = mapper.readTree(requestResource.getInputStream());
        AbstractOutgoingMessage answer = scenarioExecutor.run(mapper.readValue(mapper.writeValueAsString(requestJson), MessageToSkillDTO.class));
        JsonNode response = mapper.readTree(mapper.writeValueAsString(answer));
        assertNotNull(response);
    }
}
