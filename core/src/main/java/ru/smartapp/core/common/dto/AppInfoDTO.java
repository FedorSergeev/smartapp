package ru.smartapp.core.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppInfoDTO {
    @JsonProperty("projectId")
    private String projectId;
    @JsonProperty("applicationId")
    private String applicationId;
    @JsonProperty("appversionId")
    private String appVersionId;
    @JsonProperty("frontendEndpoint")
    private String frontendEndpoint;
    @JsonProperty("frontendType")
    private String frontendType;
    @JsonProperty("systemName")
    private String systemName;
    @JsonProperty("frontendStateId")
    private String frontendStateId;
    @JsonProperty("ageLimit")
    private Long ageLimit;
}
