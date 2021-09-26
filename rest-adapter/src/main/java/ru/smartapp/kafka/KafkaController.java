package ru.smartapp.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.smartapp.core.handlers.IncomingMessageHandler;

import static java.lang.String.format;

/**
 * RestFull API for NLP system
 *
 * @author Fedor Sergeev
 * @since 1.0
 */
@RestController("restAdapter")
@RequestMapping("smartapp")
public class KafkaController {
    private final Log log = LogFactory.getLog(getClass());

    private final ObjectMapper mapper;
    private final IncomingMessageHandler handler;

    public KafkaController(ObjectMapper mapper, IncomingMessageHandler handler) {
        this.mapper = mapper;
        this.handler = handler;
    }

    @PostMapping
    public Mono<ResponseEntity<JsonNode>> processNlpRequest(@RequestBody String string) throws JsonProcessingException {
        log.info(format("Incoming from REST webhook: %s", string));
        return Mono.just(ResponseEntity.ok(handler.handle(mapper.readTree(string))));
    }
}