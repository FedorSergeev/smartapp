package ru.smartapp.core.handlers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ru.smartapp.core.ScenariosMap;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class IncomingMessageHandlerTest {

    @Value("classpath:messageToSkill.json")
    private Resource requestResource;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private IncomingMessageHandler incomingMessageHandler;
    @Autowired
    private ScenariosMap scenariosMap;

    @Test
    public void test() throws IOException {
        assertNotNull(requestResource);
        JsonNode requestJson = mapper.readTree(requestResource.getInputStream());
        JsonNode response = incomingMessageHandler.handle(requestJson);
        assertNotNull(response);
    }
}
