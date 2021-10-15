package ru.smartapp.core.answersbuilders;

import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.NothingFoundDto;
import ru.smartapp.core.common.dto.outgoing.NothingFoundPayloadDto;

public class NothingFoundMessageBuilder implements AnswerBuilder {
    public NothingFoundDto build(AbstractIncomingMessage incomingMessage) {
        NothingFoundDto nothingFoundDTO = new NothingFoundDto();
        NothingFoundPayloadDto nothingFoundPayloadDTO = new NothingFoundPayloadDto();
        nothingFoundPayloadDTO.setDevice(incomingMessage.getDeviceDto());
        nothingFoundDTO.setMessageId(incomingMessage.getMessageId());
        nothingFoundDTO.setMessageName(MessageName.NOTHING_FOUND.name());
        nothingFoundDTO.setSessionId(incomingMessage.getSessionId());
        nothingFoundDTO.setUuidDTO(incomingMessage.getUuidDTO());
        nothingFoundDTO.setPayload(nothingFoundPayloadDTO);
        return nothingFoundDTO;
    }
}
