package ru.smartapp.core.intents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.smartapp.core.annotations.ScenarioClass;

import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.String.format;

@Service
@ScenarioClass({"run_app"})
public class SomeDumbScenario implements Scenario {

    private final Log log = LogFactory.getLog(getClass());
    @Value("classpath:response.json")
    private Resource responseResource;
    private final ObjectMapper mapper;

    @Autowired
    public SomeDumbScenario(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public JsonNode run(JsonNode incomingMessage) {
//        TODO: user answer message builder, not resource file
        try {
            ObjectNode answer = (ObjectNode) mapper.readTree(responseResource.getInputStream());
            answer.set("sessionId", incomingMessage.get("sessionId"));
            answer.set("messageId", incomingMessage.get("messageId"));
            answer.set("uuid", incomingMessage.get("uuid"));
            log.info(format("Outgoing from scenario %s: %s", getClass().getTypeName(), answer));
            return answer;
        } catch (JsonProcessingException e) {
            log.error(format("Failed to convert response to JsonNode %s", e));
        } catch (FileNotFoundException e) {
            log.error("Ti loh, net faila", e);
        } catch (IOException e) {
            log.error("Hmm", e);
        }
        // 192.168.10.29 8087
        // todo дефолтный ответ с ошибкой
        return null;
    }

}
