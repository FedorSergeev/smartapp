package ru.smartapp.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;
import ru.smartapp.core.handlers.IncomingMessageRouter;

/**
 * Consumes and produces messages from Kafka
 *
 * @author Fedor Sergeev
 * @since 1.0
 */
@Service("kafkaAdapter")
@Profile("kafka")
public class KafkaAdapter {
    private final Log log = LogFactory.getLog(getClass());

    private final Flux<ReceiverRecord<String, String>> reactiveKafkaReceiver;
    private final IncomingMessageRouter handler;
    private final ObjectMapper mapper = new ObjectMapper();

    public KafkaAdapter(Flux<ReceiverRecord<String, String>> reactiveKafkaReceiver,
                        IncomingMessageRouter handler) {
        this.reactiveKafkaReceiver = reactiveKafkaReceiver;
        this.handler = handler;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onMessage() {
        reactiveKafkaReceiver
                .doOnNext(this::runIntent)
                .doOnError(e -> log.error("KafkaFlux exception", e))
                .subscribe();
    }

    private void runIntent(ReceiverRecord<String, String> record) {
        log.info(record.value());
        try {
            JsonNode request = mapper.readTree(record.value());
            handler.handle(request);
        } catch (JsonProcessingException e) {
            log.error("Failed to read JSON from message " + record.value());
        }

    }
}