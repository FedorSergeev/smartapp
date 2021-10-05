package ru.smartapp.core.common.model;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.smartapp.core.common.dao.ScenarioDataDAO;
import ru.smartapp.core.common.dto.incoming.AbstractIncomingMessage;

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
            User user,
            String intent,
            @Nullable String stateId,
            T message,
            @Nullable ScenarioDataDAO scenarioData
    ) {
        this.user = user;
        this.intent = intent;
        this.stateId = stateId;
        this.message = message;
        this.scenarioData = scenarioData;
    }
}
