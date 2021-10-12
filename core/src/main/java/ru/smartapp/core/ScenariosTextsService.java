package ru.smartapp.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ScenariosTextsService {
    @Value("classpath*:scenario_texts/*.*")
    private Resource[] scenarioTextsResources;
    @Getter
    private Map<String, JsonNode> textMap = new HashMap<>();
    private ObjectMapper mapper;

    @Autowired
    public ScenariosTextsService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        for (Resource resource : scenarioTextsResources) {
            String nameWOExtension = FilenameUtils.removeExtension(resource.getFilename());
            if (textMap.containsKey(nameWOExtension)) {
                log.warn(String.format("Context already has text file with name '%s'", resource.getFilename()));
            }
            textMap.put(nameWOExtension, mapper.readTree(resource.getInputStream()));
        }
    }
}
