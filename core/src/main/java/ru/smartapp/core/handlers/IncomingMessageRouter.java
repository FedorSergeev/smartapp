package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.smartapp.core.answersbuilders.ErrorMessageBuilder;
import ru.smartapp.core.common.AppErrorCodes;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.incoming.CloseAppDTO;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.incoming.RunAppDTO;
import ru.smartapp.core.common.dto.incoming.ServerActionDTO;
import ru.smartapp.core.common.dto.outgoing.ErrorDTO;
import ru.smartapp.core.common.dto.outgoing.OutgoingMessage;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class IncomingMessageRouter {
    private static final String MESSAGE_NAME = "messageName";
    private final Map<MessageName, MessageHandler> incomingMessageMap;
    private final MessageToSkillHandler<MessageToSkillDTO> messageToSkillHandler;
    private final CloseAppHandler<CloseAppDTO> closeAppHandler;
    private final RunAppHandler<RunAppDTO> runAppHandler;
    private final ServerActionHandler<ServerActionDTO> serverActionHandler;

    @Autowired
    public IncomingMessageRouter(
            MessageToSkillHandler<MessageToSkillDTO> messageToSkillHandler,
            CloseAppHandler<CloseAppDTO> closeAppHandler,
            RunAppHandler<RunAppDTO> runAppHandler,
            ServerActionHandler<ServerActionDTO> serverActionHandler
    ) {
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
        if (!incomingMessage.hasNonNull(MESSAGE_NAME)) {
            log.error("Key 'messageName' must not be empty");
            return false;
        }
        JsonNode messageNameNode = incomingMessage.get(MESSAGE_NAME);
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

    public MessageHandler getIncomingMessageHandler(@NotNull JsonNode incomingMessage) {
        MessageName messageName = MessageName.valueOf(incomingMessage.get(MESSAGE_NAME).asText());
        return incomingMessageMap.get(messageName);
    }

    public Mono<OutgoingMessage> handle(JsonNode incomingMessage) throws JsonProcessingException {
        if (!validateIncomingMessage(incomingMessage)) {
            ErrorDTO errorDTO = new ErrorMessageBuilder().build(AppErrorCodes.ERROR.getCode(), "", null);
            return Mono.just(errorDTO);
        }
        return getIncomingMessageHandler(incomingMessage).handle(incomingMessage);
    }
}
