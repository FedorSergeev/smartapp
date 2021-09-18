package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import ru.smartapp.core.common.dto.incoming.CloseAppDTO;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessagesSerializationsTest {

    @Test
    public void messageToSkillTest() throws IOException {
        File file = ResourceUtils.getFile("classpath:messageToSkill.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestJson = mapper.readTree(file);
        MessageToSkillDTO messageToSkillDTO = mapper.readValue(file, MessageToSkillDTO.class);

        assertEquals(requestJson, mapper.readTree(mapper.writeValueAsString(messageToSkillDTO)));
    }

    @Test
    public void closeAppTest() throws IOException {
        File file = ResourceUtils.getFile("classpath:closeApp.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode requestJson = mapper.readTree(file);
        CloseAppDTO closeAppDTO = mapper.readValue(file, CloseAppDTO.class);

        assertEquals(requestJson, mapper.readTree(mapper.writeValueAsString(closeAppDTO)));
    }

}
