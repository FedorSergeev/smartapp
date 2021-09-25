package ru.smartapp.core;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ScenarioExecutorTest {

    public static final String REQUEST_JSON = "request.json";

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ScenarioExecutor scenarioExecutor;
    @Autowired
    private ScenariosMap scenariosMap;

    @Test
    public void test() throws IOException {
        InputStream resource = getClass().getClassLoader().getResourceAsStream(REQUEST_JSON);
        assertNotNull(resource);
        File file = new File(REQUEST_JSON);
        FileUtils.copyInputStreamToFile(resource, file);
        JsonNode requestJson = mapper.readTree(file);
        JsonNode response = scenarioExecutor.run(requestJson);
        assertNotNull(response);
    }
}
