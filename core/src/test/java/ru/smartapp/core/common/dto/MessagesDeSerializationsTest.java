package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import ru.smartapp.core.common.dto.incoming.CloseAppDTO;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.incoming.RunAppDTO;
import ru.smartapp.core.common.dto.incoming.ServerActionDTO;
import ru.smartapp.core.common.dto.outgoing.ErrorDTO;
import ru.smartapp.core.common.dto.outgoing.NothingFoundDTO;
import ru.smartapp.core.common.dto.outgoing.PolicyRunAppDTO;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessagesDeSerializationsTest {

    public static void assertEqualsJsonNodes(JsonNode one, JsonNode two) {
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

    @Test
    public void messageToSkillTest() throws IOException {
        deAndSerializationTest("classpath:messageToSkill.json", MessageToSkillDTO.class);
    }

    @Test
    public void closeAppTest() throws IOException {
        deAndSerializationTest("classpath:closeApp.json", CloseAppDTO.class);
    }

    @Test
    public void runAppTest() throws IOException {
        deAndSerializationTest("classpath:runApp.json", RunAppDTO.class);
    }

    @Test
    public void serverActionTest() throws IOException {
        deAndSerializationTest("classpath:serverAction.json", ServerActionDTO.class);
    }

    @Test
    public void errorTest() throws IOException {
        deAndSerializationTest("classpath:error.json", ErrorDTO.class);
    }

    @Test
    public void nothingFoundTest() throws IOException {
        deAndSerializationTest("classpath:nothingFound.json", NothingFoundDTO.class);
    }

    @Test
    public void policyRunAppTest() throws IOException {
        deAndSerializationTest("classpath:policyRunApp.json", PolicyRunAppDTO.class);
    }

    private void deAndSerializationTest(String fileName, Class<? extends Message> clazz) throws IOException {
        File file = ResourceUtils.getFile(fileName);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestJson = mapper.readTree(file);
        JsonNode actual = mapper.readTree(mapper.writeValueAsString(mapper.readValue(file, clazz)));
        assertEqualsJsonNodes(requestJson, actual);
    }

}
