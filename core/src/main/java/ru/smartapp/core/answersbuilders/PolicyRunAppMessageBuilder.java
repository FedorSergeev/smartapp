package ru.smartapp.core.answersbuilders;

import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;
import ru.smartapp.core.common.MessageName;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.outgoing.PolicyRunAppDTO;
import ru.smartapp.core.common.dto.outgoing.PolicyRunAppPayloadDTO;

public class PolicyRunAppMessageBuilder implements AnswerBuilder {
    public PolicyRunAppDTO build(@NotNull String projectName, @NotNull JsonNode serverAction, AbstractIncomingMessage incomingMessage) {
        PolicyRunAppDTO policyRunAppDTO = new PolicyRunAppDTO();
        PolicyRunAppPayloadDTO policyRunAppPayloadDTO = new PolicyRunAppPayloadDTO();
        policyRunAppPayloadDTO.setDevice(incomingMessage.getDeviceDTO());
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
