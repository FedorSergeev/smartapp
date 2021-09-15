package ru.smartapp.core.intents;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.String.format;

public class SomeDumbScenario implements Scenario {

    public static final String RESPONSE_JSON = "response.json";
    private final Log log = LogFactory.getLog(getClass());
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonNode run(JsonNode incomingMessage) {
        InputStream resource = getClass().getClassLoader().getResourceAsStream(RESPONSE_JSON);
        if (resource != null) {
            try {
                File file = new File(RESPONSE_JSON);
                FileUtils.copyInputStreamToFile(resource, file);
                ObjectNode answer = (ObjectNode) mapper.readTree(file);
                answer.set("sessionId", incomingMessage.get("sessionId"));
                answer.set("messageId", incomingMessage.get("messageId"));
                answer.set("uuid", incomingMessage.get("uuid"));
                log.info(format("Outgoing from scenario %s: %s", getClass().getTypeName(), answer));
                return answer;
            } catch(JsonProcessingException e){
                log.error(format("Failed to convert response to JsonNode %s", e));
            } catch(FileNotFoundException e){
                log.error(format("Ti loh, net faila %s", RESPONSE_JSON), e);
            } catch(IOException e){
                log.error("Hmm", e);
            }
        }
        // 192.168.10.29 8087
        // todo дефолтный ответ с ошибкой
        return null;
    }

}
