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
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class IncomingMessageRouterTest {

    @Value("classpath:messageToSkill.json")
    private Resource messageToSkillResource;
    @Value("classpath:closeApp.json")
    private Resource closeAppResource;
    @Value("classpath:runApp.json")
    private Resource runAppResource;
    @Value("classpath:serverAction.json")
    private Resource serverActionResource;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private IncomingMessageRouter incomingMessageRouter;
    @Autowired
    private ScenariosMap scenariosMap;

    @Test
    public void testNull() throws IOException {
        JsonNode response = incomingMessageRouter.handle(null).orElse(null);
        assertNotNull(response);
    }

    @Test
    public void testEmptyString() throws IOException {
        JsonNode response = incomingMessageRouter.handle(mapper.readTree("")).orElse(null);
        assertNotNull(response);
    }

    @Test
    public void testEmptyJson() throws IOException {
        JsonNode response = incomingMessageRouter.handle(mapper.readTree("{}")).orElse(null);
        assertNotNull(response);
    }

    @Test
    public void testMessageToSkill() throws IOException {
        JsonNode response = incomingMessageRouter.handle(mapper.readTree(messageToSkillResource.getInputStream())).orElse(null);
        assertNotNull(response);
    }

    @Test
    public void testCloseApp() throws IOException {
        JsonNode response = incomingMessageRouter.handle(mapper.readTree(closeAppResource.getInputStream())).orElse(null);
        assertNull(response);
    }

    @Test
    public void testRunApp() throws IOException {
        JsonNode response = incomingMessageRouter.handle(mapper.readTree(runAppResource.getInputStream())).orElse(null);
        assertNotNull(response);
    }

    @Test
    public void testServerAction() throws IOException {
        JsonNode response = incomingMessageRouter.handle(mapper.readTree(serverActionResource.getInputStream())).orElse(null);
        assertNotNull(response);
    }
}
