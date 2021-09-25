package ru.smartapp.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.smartapp.core.annotations.ScenarioClassMap;
import ru.smartapp.core.intents.Scenario;

import java.util.HashMap;
import java.util.Map;

@Service
@ScenarioClassMap
public class ScenariosMap {
    private Map<String, Class<? extends Scenario>> scenarioMap = new HashMap<>();

    @Nullable
    public Class<? extends Scenario> get(String scenarioId) {
        return scenarioMap.getOrDefault(scenarioId, null);
    }

    public void put(@NotNull String scenarioId, @NotNull Class<? extends Scenario> scenarioClass) {
        if (scenarioMap.containsKey(scenarioId)) {
            throw new RuntimeException(String.format("Duplicate scenario id: %s", scenarioId));
        }
        scenarioMap.put(scenarioId, scenarioClass);
    }
}
