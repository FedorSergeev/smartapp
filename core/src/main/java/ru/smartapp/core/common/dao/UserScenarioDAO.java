package ru.smartapp.core.common.dao;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@Getter
@Setter
public class UserScenarioDAO implements Serializable {

    public static final long serialVersionUID = 4521013305736905985L;

    private String userUniqueId;
    @Nullable
    private String intent;
    @Nullable
    private String stateId;
    @Nullable
    private ScenarioDataDAO scenarioData;
}
