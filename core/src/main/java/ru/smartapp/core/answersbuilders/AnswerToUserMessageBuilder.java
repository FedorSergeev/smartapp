package ru.smartapp.core.answersbuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.smartapp.core.answersbuilders.sdkanswerbuilder.SdkAnswerBuilder;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.incoming.IncomingMessage;
import ru.smartapp.core.common.dto.outgoing.AnswerToUserDto;
import ru.smartapp.core.config.SpringContext;

public class AnswerToUserMessageBuilder implements AnswerBuilder {
    public AnswerToUserDto build(
            SdkAnswerBuilder sdkAnswerBuilder,
            IncomingMessage incomingMessage
    ) throws JsonProcessingException {
        ObjectNode payload = sdkAnswerBuilder.getJson();
        ObjectMapper mapper = getMapper();
        JsonNode deviceJson = mapper.readTree(mapper.writeValueAsString(incomingMessage.getDeviceDto()));
        payload.set("device", deviceJson);
        AnswerToUserDto answerToUserDTO = new AnswerToUserDto();
        answerToUserDTO.setMessageId(incomingMessage.getMessageId());
        answerToUserDTO.setMessageName(MessageName.ANSWER_TO_USER.name());
        answerToUserDTO.setSessionId(incomingMessage.getSessionId());
        answerToUserDTO.setUuidDTO(incomingMessage.getUuidDTO());
        answerToUserDTO.setPayload(payload);
        return answerToUserDTO;
    }

    private ObjectMapper getMapper() {
        return SpringContext.getBean(ObjectMapper.class);
    }
}
