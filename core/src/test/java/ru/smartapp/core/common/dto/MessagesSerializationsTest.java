package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import ru.smartapp.core.common.dto.incoming.CloseAppDTO;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessagesSerializationsTest {

    @Test
    public void messageToSkillTest() throws IOException {
        File file = ResourceUtils.getFile("classpath:messageToSkill.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestJson = mapper.readTree(file);
        JsonNode actual = mapper.readTree(mapper.writeValueAsString(mapper.readValue(file, MessageToSkillDTO.class)));
        assertEqualsJsonNodes(requestJson, actual);
    }

    @Test
    public void closeAppTest() throws IOException {
        File file = ResourceUtils.getFile("classpath:closeApp.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestJson = mapper.readTree(file);
        JsonNode actual = mapper.readTree(mapper.writeValueAsString(mapper.readValue(file, CloseAppDTO.class)));
        assertEqualsJsonNodes(requestJson, actual);
    }

    private void assertEqualsJsonNodes(JsonNode one, JsonNode two) {
        Iterator<Map.Entry<String, JsonNode>> iterator = one.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> node = iterator.next();
            assertTrue(two.has(node.getKey()));
            if (node.getValue().getNodeType().equals(JsonNodeType.OBJECT)) {
                assertEqualsJsonNodes(node.getValue(), two.get(node.getKey()));
            } else {
                assertEquals(node.getValue(), two.get(node.getKey()));
            }
        }
    }

}
