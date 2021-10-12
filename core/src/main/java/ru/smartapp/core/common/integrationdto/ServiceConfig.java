package ru.smartapp.core.common.integrationdto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceConfig {
    @JsonProperty("name")
    private String name;
    @JsonProperty("host")
    private String host;
    @JsonProperty("endpoint")
    private String endpoint;
    @JsonProperty("port")
    private String port;
    @JsonProperty("timeout")
    private Double timeout;
}
