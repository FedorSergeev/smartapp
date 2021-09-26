package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.defaultanswers.NothingFoundMessageBuilder;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class IncomingMessageHandler {
    private Log log = LogFactory.getLog(getClass());
    // TODO
    private Map<MessageName, MessageHandler> incomingMessageMap;
    private ObjectMapper mapper;
    private MessageToSkillHandler<AbstractOutgoingMessage> messageToSkillHandler;

    @Autowired
    public IncomingMessageHandler(
            ObjectMapper mapper,
            MessageToSkillHandler<AbstractOutgoingMessage> messageToSkillHandler
    ) {
        this.mapper = mapper;
        this.messageToSkillHandler = messageToSkillHandler;
    }

    @PostConstruct
    private void postConstruct() {
        incomingMessageMap =
                // TODO
                new HashMap<MessageName, MessageHandler>() {{
                    put(MessageName.MESSAGE_TO_SKILL, messageToSkillHandler);
                    // TODO
//                    put(MessageName.CLOSE_APP, CloseAppDTO.class);
//                    put(MessageName.SERVER_ACTION, ServerActionDTO.class);
//                    put(MessageName.RUN_APP, RunAppDTO.class);
                }};
    }

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

    // TODO
    public MessageHandler getIncomingMessageHandler(@NotNull JsonNode incomingMessage) {
        MessageName messageName = MessageName.valueOf(incomingMessage.get("messageName").asText());
        return incomingMessageMap.get(messageName);
    }

    public JsonNode handle(JsonNode incomingMessage) {
        JsonNode result = null;
        try {
            if (!validateIncomingMessage(incomingMessage)) {
                log.error("Got invalid incoming message");
                return mapper.readTree(mapper.writeValueAsString(new NothingFoundMessageBuilder().run()));
            }
            // TODO
            MessageHandler handler = getIncomingMessageHandler(incomingMessage);
            MessageToSkillDTO message = mapper.readValue(mapper.writeValueAsString(incomingMessage), MessageToSkillDTO.class);
            // TODO
            AbstractOutgoingMessage answer = handler.handle(message);
            result = mapper.readTree(mapper.writeValueAsString(answer));
        } catch (JsonProcessingException e) {
            log.error("Json cast exception", e);
        }
        return result;
    }
}
