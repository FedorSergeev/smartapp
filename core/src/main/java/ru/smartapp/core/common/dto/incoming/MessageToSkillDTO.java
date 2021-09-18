package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import ru.smartapp.core.common.dto.AbstractMessage;
import ru.smartapp.core.common.dto.AppInfoDTO;
import ru.smartapp.core.common.dto.DeviceDTO;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageToSkillDTO extends AbstractMessage implements IncomingMessage {
    @JsonProperty("payload")
    private MessageToSkillPayloadDTO payload;
}
