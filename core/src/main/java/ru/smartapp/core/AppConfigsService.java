package ru.smartapp.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.smartapp.core.common.integrationdto.IntegrationAppConfig;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class AppConfigsService {
    private final ObjectMapper mapper;
    @Value("classpath:configs/integration_app_config.json")
    private Resource integrationAppConfigResource;
    @Getter
    private IntegrationAppConfig integrationAppConfig;

    @Autowired
    public AppConfigsService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    private void postConstruct() throws IOException {
        integrationAppConfig = mapper.readValue(integrationAppConfigResource.getInputStream(), IntegrationAppConfig.class);
    }
}
