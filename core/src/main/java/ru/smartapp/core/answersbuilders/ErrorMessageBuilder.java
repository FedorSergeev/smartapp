package ru.smartapp.core.answersbuilders;

import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.DeviceDto;
import ru.smartapp.core.common.dto.UuidDto;
import ru.smartapp.core.common.dto.incoming.IncomingMessage;
import ru.smartapp.core.common.dto.outgoing.ErrorDto;
import ru.smartapp.core.common.dto.outgoing.ErrorPayloadDto;

import java.util.Optional;

public class ErrorMessageBuilder implements AnswerBuilder {

    public ErrorDto build(Long code, String description, @Nullable IncomingMessage incomingMessage) {
        ErrorDto errorDTO = new ErrorDto();
        ErrorPayloadDto errorPayloadDTO = new ErrorPayloadDto();
        errorPayloadDTO.setCode(code);
        errorPayloadDTO.setDescription(description);
        Optional<IncomingMessage> incomingMessageOptional = Optional.ofNullable(incomingMessage);
        DeviceDto device = incomingMessageOptional.map(IncomingMessage::getDeviceDto).orElse(null);
        errorPayloadDTO.setDevice(device);
        Long messageId = incomingMessageOptional.map(IncomingMessage::getMessageId).orElse(null);
        errorDTO.setMessageId(messageId);
        errorDTO.setMessageName(MessageName.ERROR.name());
        String sessionId = incomingMessageOptional.map(IncomingMessage::getSessionId).orElse(null);
        errorDTO.setSessionId(sessionId);
        UuidDto uuidDTO = incomingMessageOptional.map(IncomingMessage::getUuidDTO).orElse(null);
        errorDTO.setUuidDTO(uuidDTO);
        errorDTO.setPayload(errorPayloadDTO);
        return errorDTO;
    }

}
