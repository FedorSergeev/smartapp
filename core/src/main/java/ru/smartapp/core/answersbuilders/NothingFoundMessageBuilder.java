package ru.smartapp.core.answersbuilders;

import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.NothingFoundDTO;
import ru.smartapp.core.common.dto.outgoing.NothingFoundPayloadDTO;

public class NothingFoundMessageBuilder implements AnswerBuilder {
    public NothingFoundDTO build(AbstractIncomingMessage incomingMessage) {
        NothingFoundDTO nothingFoundDTO = new NothingFoundDTO();
        NothingFoundPayloadDTO nothingFoundPayloadDTO = new NothingFoundPayloadDTO();
        nothingFoundPayloadDTO.setDevice(incomingMessage.getDeviceDTO());
        nothingFoundDTO.setMessageId(incomingMessage.getMessageId());
        nothingFoundDTO.setMessageName(MessageName.NOTHING_FOUND.name());
        nothingFoundDTO.setSessionId(incomingMessage.getSessionId());
        nothingFoundDTO.setUuidDTO(incomingMessage.getUuidDTO());
        nothingFoundDTO.setPayload(nothingFoundPayloadDTO);
        return nothingFoundDTO;
    }
}
