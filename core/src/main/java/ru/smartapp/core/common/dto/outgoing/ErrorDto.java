package ru.smartapp.core.common.dto.outgoing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto extends AbstractOutgoingMessage {
    @JsonProperty("payload")
    private ErrorPayloadDto payload;
}
