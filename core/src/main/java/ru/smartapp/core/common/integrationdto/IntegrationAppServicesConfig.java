package ru.smartapp.core.common.integrationdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class IntegrationAppServicesConfig {
    @JsonProperty("service_one")
    private ServiceConfig serviceOne;
    @JsonProperty("service_two")
    private ServiceConfig serviceTwo;
}
