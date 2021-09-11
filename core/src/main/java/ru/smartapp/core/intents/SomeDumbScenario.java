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
            String jsonString = "{\"message_name\": \"ANSWER_TO_USER\", \"payload\": {\"pronounceText\": \"darova\", \"items\": [{\"bubble\": {\"text\": \"darova\", \"markdown\": true}}]}}";
            JsonNode answer = mapper.readTree(jsonString);
            log.info(format("Outgoing from scenario %s: %s", getClass().getTypeName(), answer.toString()));
            return answer;
        } catch (JsonProcessingException e) {
            log.error(format("Failed to convert response to JsonNode %s", e));
        }
        // 192.168.10.29 8087
        // todo дефолтный ответ с ошибкой
        return null;
    }

}
