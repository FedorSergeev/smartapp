package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smartapp.core.cache.CacheAdapter;
import ru.smartapp.core.common.dto.incoming.CloseAppDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.model.User;

import java.util.Optional;

@Component
public class CloseAppHandler<I extends CloseAppDTO> extends AbstractMessageHandler<I> {
    private final Log log = LogFactory.getLog(getClass());
    private ObjectMapper mapper;
    private CacheAdapter cacheAdapter;

    @Autowired
    public CloseAppHandler(ObjectMapper mapper, CacheAdapter cacheAdapter) {
        this.mapper = mapper;
        this.cacheAdapter = cacheAdapter;
    }

    public Optional<AbstractOutgoingMessage> handle(JsonNode incomingMessage) throws JsonProcessingException {
        CloseAppDTO dto = convert(incomingMessage);
        cacheAdapter.deleteUserScenario(new User(dto).getUserUniqueId());
        return Optional.empty();
    }

    @Override
    I convert(JsonNode incomingMessage) throws JsonProcessingException {
        return mapper.readValue(mapper.writeValueAsString(incomingMessage), new TypeReference<I>() {
        });
    }
}
