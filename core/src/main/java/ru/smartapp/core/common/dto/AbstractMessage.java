package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractMessage implements Message {

    @JsonProperty("messageId")
    private Long messageId;

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("messageName")
    private String messageName;

    @JsonProperty("uuid")
    private UuidDTO UUIDDTO;

}
