package ru.smartapp.core.answersbuilders;

import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.DeviceDTO;
import ru.smartapp.core.common.dto.UuidDTO;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.ErrorDTO;
import ru.smartapp.core.common.dto.outgoing.ErrorPayloadDTO;

import java.util.Optional;

public class ErrorMessageBuilder implements AnswerBuilder {

    public ErrorDTO build(Long code, String description, @Nullable AbstractIncomingMessage incomingMessage) {
        ErrorDTO errorDTO = new ErrorDTO();
        ErrorPayloadDTO errorPayloadDTO = new ErrorPayloadDTO();
        errorPayloadDTO.setCode(code);
        errorPayloadDTO.setDescription(description);
        Optional<AbstractIncomingMessage> incomingMessageOptional = Optional.ofNullable(incomingMessage);
        DeviceDTO device = incomingMessageOptional.map(AbstractIncomingMessage::getDeviceDTO).orElse(null);
        errorPayloadDTO.setDevice(device);
        Long messageId = incomingMessageOptional.map(AbstractIncomingMessage::getMessageId).orElse(null);
        errorDTO.setMessageId(messageId);
        errorDTO.setMessageName(MessageName.ERROR.name());
        String sessionId = incomingMessageOptional.map(AbstractIncomingMessage::getSessionId).orElse(null);
        errorDTO.setSessionId(sessionId);
        UuidDTO uuidDTO = incomingMessageOptional.map(AbstractIncomingMessage::getUuidDTO).orElse(null);
        errorDTO.setUuidDTO(uuidDTO);
        errorDTO.setPayload(errorPayloadDTO);
        return errorDTO;
    }

}
