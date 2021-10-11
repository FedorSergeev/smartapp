package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class AbstractMessage implements Message {

    @JsonProperty("messageId")
    private Long messageId;

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("messageName")
    private String messageName;

    @JsonProperty("uuid")
    private UuidDTO uuidDTO;

}
