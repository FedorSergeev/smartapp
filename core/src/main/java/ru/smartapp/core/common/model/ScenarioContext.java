package ru.smartapp.core.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.cache.CacheAdapter;
import ru.smartapp.core.common.Character;
import ru.smartapp.core.common.dao.ScenarioDataDAO;
import ru.smartapp.core.common.dao.UserScenarioDAO;
import ru.smartapp.core.common.dto.CharacterDTO;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;
import ru.smartapp.core.common.dto.incoming.MessageToSkillDTO;
import ru.smartapp.core.common.dto.incoming.MessageToSkillPayloadDTO;
import ru.smartapp.core.config.SpringContext;

import java.util.Arrays;
import java.util.Optional;

@Getter
@Setter
@Slf4j
public class ScenarioContext {
    private User user;
    private String intent;
    private Character character;
    @Nullable
    private String stateId;
    @Nullable
    private ScenarioDataDAO scenarioData;
    private AbstractIncomingMessage message;

    public ScenarioContext(
            String intent,
            AbstractIncomingMessage message
    ) {
        this.user = new User(message);
        this.intent = intent;
        this.character = getCharacterFromMessage(message);
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

    private boolean isNewSession(AbstractIncomingMessage message) {
        if (message instanceof MessageToSkillDTO) {
            MessageToSkillDTO messageToSkillDTO = (MessageToSkillDTO) message;
            return Optional.of(messageToSkillDTO).map(MessageToSkillDTO::getPayload).map(MessageToSkillPayloadDTO::getNewSession).orElse(false);
        }
        return false;
    }

    private Character getCharacterFromMessage(AbstractIncomingMessage message) {
        Optional<String> optionalCharacterId = Optional.ofNullable(message.getCharacterDTO()).map(CharacterDTO::getId);
        if (!optionalCharacterId.isPresent()) {
            log.warn(String.format("Expected character ids: %s, got null", Arrays.toString(Character.values())));
        }
        return Character.valueById(optionalCharacterId.orElse(Character.SBER.getId()));
    }
}
