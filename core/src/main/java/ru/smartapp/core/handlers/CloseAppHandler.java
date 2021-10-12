package ru.smartapp.core.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smartapp.core.cache.CacheAdapter;
import ru.smartapp.core.common.dto.incoming.CloseAppDTO;
import ru.smartapp.core.common.dto.outgoing.AbstractOutgoingMessage;
import ru.smartapp.core.common.model.User;

import java.util.Optional;

@Slf4j
@Component
public class CloseAppHandler<I extends CloseAppDTO> extends AbstractMessageHandler<I> {
    private ObjectMapper mapper;
    private CacheAdapter cacheAdapter;

    @Autowired
    public CloseAppHandler(ObjectMapper mapper, CacheAdapter cacheAdapter) {
        this.mapper = mapper;
        this.cacheAdapter = cacheAdapter;
    }

    public Optional<AbstractOutgoingMessage> handle(JsonNode incomingMessage) throws JsonProcessingException {
        CloseAppDTO dto = convert(incomingMessage);
        User user = new User(dto);
        log.info(String.format("Removing cache for user '%s' with unique id '%s'", user.getUserId(), user.getUserUniqueId()));
        cacheAdapter.deleteUserScenario(user.getUserUniqueId());
        return Optional.empty();
    }

    @Override
    I convert(JsonNode incomingMessage) throws JsonProcessingException {
        return mapper.readValue(mapper.writeValueAsString(incomingMessage), new TypeReference<I>() {
        });
    }
}
