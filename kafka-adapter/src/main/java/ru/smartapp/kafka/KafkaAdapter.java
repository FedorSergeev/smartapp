package ru.smartapp.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import ru.smartapp.core.ScenarioExecutor;
import ru.smartapp.core.handlers.IncomingMessageRouter;

import java.util.Date;
import java.util.UUID;

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
    private final KafkaSender<String, String> reactiveKafkaSender;
    private final ObjectMapper mappper = new ObjectMapper();
    private final IncomingMessageRouter handler;
    private final ObjectMapper mapper = new ObjectMapper();

    public KafkaAdapter(Flux<ReceiverRecord<String, String>> reactiveKafkaReceiver,
                        IncomingMessageRouter handler,
                        KafkaSender<String, String> reactiveKafkaSender) {
        this.reactiveKafkaReceiver = reactiveKafkaReceiver;
        this.reactiveKafkaSender = reactiveKafkaSender;
        this.handler = handler;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onMessage() {
        reactiveKafkaReceiver
                .doOnNext(this::runIntent)
                .doOnError(e -> log.error("KafkaFlux exception", e))
                .subscribe();
    }

    /**
     * Process request by intent scenario executor and send result to answer topic
     *
     * @param record incoming record
     */
    private void runIntent(ReceiverRecord<String, String> record) {
        log.info(record.value());
        try {
            JsonNode request = mappper.readTree(record.value());
            handler.handle(request).map( result ->
            reactiveKafkaSender.send(Mono.just(SenderRecord.create("toDP", record.partition(), new Date().getTime(), record.key(), result.toPrettyString(), UUID.randomUUID().toString())))
                    .doOnError(e -> log.error("Send failed", e))
                    .subscribe(r -> {
                        RecordMetadata metadata = r.recordMetadata();
                       log.info(String.format("Message %d sent successfully, topic-partition=%s-%d offset=%d timestamp=%s\n",
                                r.correlationMetadata(),
                                metadata.topic(),
                                metadata.partition(),
                                metadata.offset(),
                                new Date(metadata.timestamp())));
                    }));

        } catch (JsonProcessingException e) {
            log.error("Failed to read JSON from message " + record.value());
        }

    }
}