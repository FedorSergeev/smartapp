package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smartapp.core.common.dto.incoming.CloseAppDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;

import java.util.Optional;

@Component
public class CloseAppHandler<T extends AbstractOutgoingMessage> extends AbstractMessageHandler<CloseAppDTO, T> {
    private final Log log = LogFactory.getLog(getClass());
    private final ObjectMapper mapper;

    @Autowired
    public CloseAppHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Optional<T> handle(JsonNode incomingMessage) throws JsonProcessingException {
        // TODO implement
        CloseAppDTO dto = convert(incomingMessage);
        log.info("Closing something");
        return Optional.empty();
    }

    @Override
    CloseAppDTO convert(JsonNode incomingMessage) throws JsonProcessingException {
        return mapper.readValue(mapper.writeValueAsString(incomingMessage), CloseAppDTO.class);
    }
}
