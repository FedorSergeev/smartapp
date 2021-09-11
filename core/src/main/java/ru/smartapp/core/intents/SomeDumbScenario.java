package ru.smartapp.core.intents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.smartapp.core.ScenarioContext;

import static java.lang.String.format;

public class SomeDumbScenario implements Scenario {

    private final Log log = LogFactory.getLog(getClass());
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonNode run(ScenarioContext scenarioContext) {
        try {
            JsonNode jsonNode = mapper.readTree("{key, value}");
            String jsonString = "{\"pronounceText\": \"darova\",\"items\": [{\"bubble\": {\"text\": \"darova\",\"markdown\": True}}]}";
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            log.error(format("Failed to convert response to JsonNode %s", e));
        }
        // 192.168.10.29 8087
        // todo дефолтный ответ с ошибкой
        return null;
    }

}
