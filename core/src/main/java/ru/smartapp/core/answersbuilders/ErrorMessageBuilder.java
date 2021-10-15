package ru.smartapp.core.answersbuilders;

import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.DeviceDto;
import ru.smartapp.core.common.dto.UuidDto;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.ErrorDto;
import ru.smartapp.core.common.dto.outgoing.ErrorPayloadDto;

import java.util.Optional;

public class ErrorMessageBuilder implements AnswerBuilder {

    public ErrorDto build(Long code, String description, @Nullable AbstractIncomingMessage incomingMessage) {
        ErrorDto errorDTO = new ErrorDto();
        ErrorPayloadDto errorPayloadDTO = new ErrorPayloadDto();
        errorPayloadDTO.setCode(code);
        errorPayloadDTO.setDescription(description);
        Optional<AbstractIncomingMessage> incomingMessageOptional = Optional.ofNullable(incomingMessage);
        DeviceDto device = incomingMessageOptional.map(AbstractIncomingMessage::getDeviceDto).orElse(null);
        errorPayloadDTO.setDevice(device);
        Long messageId = incomingMessageOptional.map(AbstractIncomingMessage::getMessageId).orElse(null);
        errorDTO.setMessageId(messageId);
        errorDTO.setMessageName(MessageName.ERROR.name());
        String sessionId = incomingMessageOptional.map(AbstractIncomingMessage::getSessionId).orElse(null);
        errorDTO.setSessionId(sessionId);
        UuidDto uuidDTO = incomingMessageOptional.map(AbstractIncomingMessage::getUuidDTO).orElse(null);
        errorDTO.setUuidDTO(uuidDTO);
        errorDTO.setPayload(errorPayloadDTO);
        return errorDTO;
    }

}
