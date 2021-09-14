package ru.smartapp.core;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;
import ru.smartapp.core.intents.SomeDumbScenario;

import java.io.File;
import java.io.IOException;

public class ScenarioExecutorTest {

    @Autowired
    private ScenarioExecutor scenarioExecutor;

    @Autowired
    private ObjectMapper mapper;

//    TODO implement
    @Test
    public void test() throws IOException {
        File file = ResourceUtils.getFile("classpath:request.json");
        JsonNode requestJson = new ObjectMapper().readTree(file);
        ScenariosMapping scenariosMapping = new ScenariosMapping();
        scenariosMapping.put("run_app", SomeDumbScenario.class);
        ScenarioExecutor scenarioExecutor = new ScenarioExecutor(scenariosMapping);
        JsonNode response = scenarioExecutor.run(requestJson);
    }
}
