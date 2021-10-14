package ru.smartapp.core.handlers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import ru.smartapp.core.ScenariosMap;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
        OutgoingMessage outgoingMessage = incomingMessageRouter.handle(null).block();
        JsonNode response = mapper.readTree(mapper.writeValueAsString(outgoingMessage));
        assertNotNull(response);
        assertEquals(MessageName.ERROR.name(), response.get("messageName").asText());
    }

    @Test
    public void testEmptyString() throws IOException {
        OutgoingMessage outgoingMessage = incomingMessageRouter.handle(mapper.readTree("")).block();
        JsonNode response = mapper.readTree(mapper.writeValueAsString(outgoingMessage));
        assertNotNull(response);
        assertEquals(MessageName.ERROR.name(), response.get("messageName").asText());
    }

    @Test
    public void testEmptyJson() throws IOException {
        OutgoingMessage outgoingMessage = incomingMessageRouter.handle(mapper.readTree("{}")).block();
        JsonNode response = mapper.readTree(mapper.writeValueAsString(outgoingMessage));
        assertNotNull(response);
        assertEquals(MessageName.ERROR.name(), response.get("messageName").asText());
    }

    @Test
    public void testMessageToSkill() throws IOException {
        OutgoingMessage outgoingMessage = incomingMessageRouter.handle(mapper.readTree(messageToSkillResource.getInputStream())).block();
        JsonNode response = mapper.readTree(mapper.writeValueAsString(outgoingMessage));
        assertNotNull(response);
        assertNotEquals(MessageName.ERROR.name(), response.get("messageName").asText());
        assertNotEquals(MessageName.NOTHING_FOUND.name(), response.get("messageName").asText());
    }

    @Test
    public void testCloseApp() throws IOException {
        OutgoingMessage outgoingMessage = incomingMessageRouter.handle(mapper.readTree(closeAppResource.getInputStream())).block();
        assertNull(outgoingMessage);
    }

    @Test
    public void testRunApp() throws IOException {
        OutgoingMessage outgoingMessage = incomingMessageRouter.handle(mapper.readTree(runAppResource.getInputStream())).block();
        JsonNode response = mapper.readTree(mapper.writeValueAsString(outgoingMessage));
        assertNotNull(response);
        assertNotEquals(MessageName.ERROR.name(), response.get("messageName").asText());
        assertNotEquals(MessageName.NOTHING_FOUND.name(), response.get("messageName").asText());
    }

    // TODO: исправить логику обработки serverAction
//    @Test
//    public void testServerAction() throws IOException {
//        OutgoingMessage outgoingMessage = incomingMessageRouter.handle(mapper.readTree(serverActionResource.getInputStream())).block();
//        JsonNode response = mapper.readTree(mapper.writeValueAsString(outgoingMessage));
//        assertNotNull(response);
//        assertNotEquals(MessageName.ERROR.name(), response.get("messageName").asText());
//        assertNotEquals(MessageName.NOTHING_FOUND.name(), response.get("messageName").asText());
//    }
}
