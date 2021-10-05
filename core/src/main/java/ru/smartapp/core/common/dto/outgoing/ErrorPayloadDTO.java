package ru.smartapp.core.common.dto.outgoing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import ru.smartapp.core.common.dto.DeviceDTO;

@Getter
@Setter
public class ErrorPayloadDTO extends AbstractOutgoingMessage {
    @JsonProperty("code")
    private Long code;
    @JsonProperty("description")
    private String description;
    @JsonProperty("intent")
    private String intent;
    @JsonProperty("device")
    private DeviceDTO device;
}
