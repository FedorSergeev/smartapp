package ru.smartapp.core.common.dto.outgoing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolicyRunAppDTO extends AbstractOutgoingMessage {
    @JsonProperty("payload")
    private PolicyRunAppPayloadDTO payload;
}
