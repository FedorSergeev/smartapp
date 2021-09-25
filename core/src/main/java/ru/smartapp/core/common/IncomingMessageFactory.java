package ru.smartapp.core.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.smartapp.core.common.dto.incoming.CloseAppDTO;
import ru.smartapp.core.common.dto.incoming.IncomingMessage;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class IncomingMessageFactory {
    private final Log log = LogFactory.getLog(getClass());
    private final ObjectMapper mapper;

    public IncomingMessageFactory(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private final Map<MessageName, Class<? extends IncomingMessage>> incomingMessageMap = new HashMap<MessageName, Class<? extends IncomingMessage>>() {{
        put(MessageName.MESSAGE_TO_SKILL, MessageToSkillDTO.class);
        put(MessageName.CLOSE_APP, CloseAppDTO.class);
    }};

    public Boolean validateIncomingMessage(@Nullable JsonNode incomingMessage) {
        if (incomingMessage == null) {
            log.error("Incoming message must not be null");
            return false;
        }
        if (!incomingMessage.hasNonNull("messageName")) {
            log.error("Key 'messageName' must not be empty");
            return false;
        }
        JsonNode messageNameNode = incomingMessage.get("messageName");
        if (!messageNameNode.isTextual()) {
            log.error(String.format("Key's 'messageName' value must be textual, got: %s", messageNameNode));
            return false;
        }
        String messageNameString = messageNameNode.asText();
        if (Arrays.stream(MessageName.values()).noneMatch(value -> value.toString().equals(messageNameString))) {
            log.error(String.format("Unknown messageName: %s, expected: %s", messageNameString, Arrays.toString(MessageName.values())));
            return false;
        }
        MessageName messageName = MessageName.valueOf(messageNameString);
        if (!incomingMessageMap.containsKey(messageName)) {
            log.error(String.format("Cannot find any handler for enum %s", messageName));
            return false;
        }
        return true;
    }

    public IncomingMessage getIncomingMessage(@NotNull JsonNode incomingMessage) throws JsonProcessingException {
        MessageName messageName = MessageName.valueOf(incomingMessage.get("messageName").asText());
        return mapper.treeToValue(incomingMessage, incomingMessageMap.get(messageName));
    }

}
