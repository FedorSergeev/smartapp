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
import ru.smartapp.core.answersbuilders.ErrorMessageBuilder;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.incoming.*;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class IncomingMessageRouter {
    private final Log log = LogFactory.getLog(getClass());
    private Map<MessageName, MessageHandler<? extends AbstractIncomingMessage>> incomingMessageMap;
    private ObjectMapper mapper;
    private MessageToSkillHandler<MessageToSkillDTO> messageToSkillHandler;
    private CloseAppHandler<CloseAppDTO> closeAppHandler;
    private RunAppHandler<RunAppDTO> runAppHandler;
    private ServerActionHandler<ServerActionDTO> serverActionHandler;

    @Autowired
    public IncomingMessageRouter(
            ObjectMapper mapper,
            MessageToSkillHandler<MessageToSkillDTO> messageToSkillHandler,
            CloseAppHandler<CloseAppDTO> closeAppHandler,
            RunAppHandler<RunAppDTO> runAppHandler,
            ServerActionHandler<ServerActionDTO> serverActionHandler
    ) {
        this.mapper = mapper;
        this.messageToSkillHandler = messageToSkillHandler;
        this.closeAppHandler = closeAppHandler;
        this.runAppHandler = runAppHandler;
        this.serverActionHandler = serverActionHandler;
        incomingMessageMap = new HashMap<>();
    }

    @PostConstruct
    private void postConstruct() {
        incomingMessageMap.put(MessageName.MESSAGE_TO_SKILL, messageToSkillHandler);
        incomingMessageMap.put(MessageName.CLOSE_APP, closeAppHandler);
        incomingMessageMap.put(MessageName.RUN_APP, runAppHandler);
        incomingMessageMap.put(MessageName.SERVER_ACTION, serverActionHandler);
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
            log.error(String.format("Unknown messageName: %s, expected: %s",
                    messageNameString, Arrays.toString(MessageName.values())));
            return false;
        }
        MessageName messageName = MessageName.valueOf(messageNameString);
        if (!incomingMessageMap.containsKey(messageName)) {
            log.error(String.format("Cannot find any handler for enum %s", messageName));
            return false;
        }
        return true;
    }

    public MessageHandler<? extends AbstractIncomingMessage>
    getIncomingMessageHandler(@NotNull JsonNode incomingMessage) {
        MessageName messageName = MessageName.valueOf(incomingMessage.get("messageName").asText());
        return incomingMessageMap.get(messageName);
    }

    public Optional<JsonNode> handle(JsonNode incomingMessage) {
        JsonNode result = null;
        try {
            if (!validateIncomingMessage(incomingMessage)) {
                return Optional.ofNullable(mapper.readTree(mapper.writeValueAsString(new ErrorMessageBuilder().build())));
            }
            Optional<? extends AbstractOutgoingMessage> optional =
                    getIncomingMessageHandler(incomingMessage).handle(incomingMessage);
            if (optional.isPresent()) {
                AbstractOutgoingMessage answer = optional.get();
                result = mapper.readTree(mapper.writeValueAsString(answer));
            }
        } catch (JsonProcessingException e) {
            log.error("Json cast exception", e);
        }
        return Optional.ofNullable(result);
    }
}
