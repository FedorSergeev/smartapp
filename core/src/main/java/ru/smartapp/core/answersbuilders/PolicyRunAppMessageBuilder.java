package ru.smartapp.core.answersbuilders;

import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.incoming.IncomingMessage;
import ru.smartapp.core.common.dto.outgoing.PolicyRunAppDto;
import ru.smartapp.core.common.dto.outgoing.PolicyRunAppPayloadDto;

public class PolicyRunAppMessageBuilder implements AnswerBuilder {
    public PolicyRunAppDto build(@NotNull String projectName, @NotNull JsonNode serverAction, IncomingMessage incomingMessage) {
        PolicyRunAppDto policyRunAppDTO = new PolicyRunAppDto();
        PolicyRunAppPayloadDto policyRunAppPayloadDTO = new PolicyRunAppPayloadDto();
        policyRunAppPayloadDTO.setDevice(incomingMessage.getDeviceDto());
        policyRunAppPayloadDTO.setProjectName(projectName);
        policyRunAppPayloadDTO.setServerAction(serverAction);

        policyRunAppDTO.setPayload(policyRunAppPayloadDTO);
        policyRunAppDTO.setMessageId(incomingMessage.getMessageId());
        policyRunAppDTO.setMessageName(MessageName.POLICY_RUN_APP.name());
        policyRunAppDTO.setSessionId(incomingMessage.getSessionId());
        policyRunAppDTO.setUuidDTO(incomingMessage.getUuidDTO());
        return policyRunAppDTO;
    }
}
