package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UuidDTO {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("userChannel")
    private String userChannel;

}
