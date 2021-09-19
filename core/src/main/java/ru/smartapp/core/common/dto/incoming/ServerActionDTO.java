package ru.smartapp.core.common.dto.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import ru.smartapp.core.common.dto.AbstractMessage;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerActionDTO extends AbstractMessage implements IncomingMessage {
    @JsonProperty("payload")
    private JsonNode payload;
}
