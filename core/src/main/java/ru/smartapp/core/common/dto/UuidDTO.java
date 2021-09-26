package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UuidDTO {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("sub")
    private String sub;

    @JsonProperty("userChannel")
    private String userChannel;

}
