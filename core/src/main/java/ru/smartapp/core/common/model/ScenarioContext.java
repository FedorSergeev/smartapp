package ru.smartapp.core.common.model;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.cache.CacheAdapter;
import ru.smartapp.core.common.dao.ScenarioDataDAO;
import ru.smartapp.core.common.dao.UserScenarioDAO;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.incoming.MessageToSkillPayloadDTO;
import ru.smartapp.core.config.SpringContext;

import java.util.Optional;

@Getter
@Setter
public class ScenarioContext<T extends AbstractIncomingMessage> {
    private User user;
    private String intent;
    @Nullable
    private String stateId;
    @Nullable
    private ScenarioDataDAO scenarioData;
    private T message;

    public ScenarioContext(
            String intent,
            T message
    ) {
        this.user = new User(message);
        this.intent = intent;
        this.message = message;

        CacheAdapter cacheAdapter = getCacheAdapter();
        Optional<UserScenarioDAO> userScenarioOptional = cacheAdapter.getUserScenario(user.getUserUniqueId());
        intent = userScenarioOptional.map(UserScenarioDAO::getIntent).orElse(null);
        if (this.intent == null || !this.intent.equals(intent) || isNewSession(message)) {
            this.stateId = null;
            this.scenarioData = null;
        } else {
            this.stateId = userScenarioOptional.map(UserScenarioDAO::getStateId).orElse(null);
            this.scenarioData = userScenarioOptional.map(UserScenarioDAO::getScenarioData).orElse(null);
        }
    }

    private CacheAdapter getCacheAdapter() {
        return SpringContext.getBean(CacheAdapter.class);
    }

    private boolean isNewSession(T message) {
        if (message instanceof MessageToSkillDTO) {
            MessageToSkillDTO messageToSkillDTO = (MessageToSkillDTO) message;
            return Optional.of(messageToSkillDTO).map(MessageToSkillDTO::getPayload).map(MessageToSkillPayloadDTO::getNewSession).orElse(false);
        }
        return false;
    }
}
