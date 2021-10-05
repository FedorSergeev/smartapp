package ru.smartapp.core.common.dto.outgoing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import ru.smartapp.core.common.dto.DeviceDTO;

@Getter
@Setter
public class NothingFoundPayloadDTO extends AbstractOutgoingMessage {
    @JsonProperty("device")
    private DeviceDTO device;
    @JsonProperty("intent")
    private String intent;
}
