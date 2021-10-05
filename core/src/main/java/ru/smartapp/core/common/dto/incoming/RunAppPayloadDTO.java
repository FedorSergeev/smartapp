package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import ru.smartapp.core.common.dto.AppInfoDTO;
import ru.smartapp.core.common.dto.CharacterDTO;
import ru.smartapp.core.common.dto.DeviceDTO;

@Getter
@Setter
public class RunAppPayloadDTO {
    @JsonProperty("device")
    private DeviceDTO device;
    @JsonProperty("app_info")
    private AppInfoDTO appInfo;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("intent")
    private String intent;
    @JsonProperty("character")
    private CharacterDTO character;
    @JsonProperty("strategies")
    private JsonNode strategies;
    @JsonProperty("server_action")
    private JsonNode serverAction;
}
