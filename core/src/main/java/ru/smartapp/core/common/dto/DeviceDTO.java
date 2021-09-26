package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDTO {
    @JsonProperty("platformType")
    private String platformType;
    @JsonProperty("platformVersion")
    private String platformVersion;
    @JsonProperty("surface")
    private String surface;
    @JsonProperty("surfaceVersion")
    private String surfaceVersion;
    @JsonProperty("deviceId")
    private String deviceId;
    @JsonProperty("deviceManufacturer")
    private String deviceManufacturer;
    @JsonProperty("deviceModel")
    private String deviceModel;
    @JsonProperty("features")
    private JsonNode features;
    @JsonProperty("capabilities")
    private JsonNode capabilities;
    @JsonProperty("additionalInfo")
    private JsonNode additionalInfo;
}
