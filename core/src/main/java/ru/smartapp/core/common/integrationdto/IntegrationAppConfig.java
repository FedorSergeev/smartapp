package ru.smartapp.core.common.integrationdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class IntegrationAppConfig {
    @JsonProperty("services")
    private IntegrationAppServicesConfig services;
}

