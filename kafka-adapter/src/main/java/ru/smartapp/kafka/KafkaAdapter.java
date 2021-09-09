package ru.smartapp.kafka;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("kafka")
@Service("kafkaAdapter")
public class KafkaAdapter {
    private final Log log = LogFactory.getLog(getClass());
}
