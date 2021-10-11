package ru.smartapp.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("kafka")
public class ReactiveKafkaConfiguration {
    @Bean
    Map<String, Object> kafkaConsumerConfiguration() {
        Map<String, Object> configuration = new HashMap<>();
        configuration.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        configuration.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
        configuration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return configuration;
    }

    @Bean
    ReceiverOptions<String, String> kafkaReceiverOptions(@Value("${kafka.topic.in}") String[] inTopicName) {
        ReceiverOptions<String, String> options = ReceiverOptions.create(kafkaConsumerConfiguration());
        return options.subscription(Arrays.asList(inTopicName))
                .withKeyDeserializer(new StringDeserializer())
                .withValueDeserializer(new StringDeserializer());
    }

    @Bean
    Flux<ReceiverRecord<String, String>> reactiveKafkaReceiver(ReceiverOptions<String, String> kafkaReceiverOptions) {
        return KafkaReceiver.create(kafkaReceiverOptions).receive();
    }
}
