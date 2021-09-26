package ru.smartapp.core.common.dto.outgoing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO extends AbstractOutgoingMessage {
    @JsonProperty("payload")
    private JsonNode payload;
}
