package ru.smartapp.core;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.smartapp.core.intents.Scenario;
import ru.smartapp.core.intents.SomeDumbScenario;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service("scenarioMapping")
public class ScenariosMapping {
    private Map<String, Class<? extends Scenario>> scenarioMap = new HashMap<>();

    @PostConstruct
    private void postConstruct() {
        scenarioMap.put("run_app", SomeDumbScenario.class);
    }

    @Nullable
    public Class<? extends Scenario> get(String scenarioId) {
        return scenarioMap.getOrDefault(scenarioId, null);
    }

    public void put(@NotNull String scenarioId, @NotNull Class<Scenario> scenarioClass) {
        if (scenarioMap.containsKey(scenarioId)) {
            throw new RuntimeException(String.format("Duplicate scenario id: %s", scenarioId));
        }
        scenarioMap.put(scenarioId, scenarioClass);
    }
}
