package ru.smartapp.core.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import ru.smartapp.core.common.dao.UserScenarioDAO;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Optional.ofNullable;
import static ru.smartapp.core.config.Config.USER_SCENARIO_CACHE_NAME;

// TODO: настроить время жизни кеша
@Slf4j
@Service("cacheAdapter")
public class CacheAdapter {
    @Autowired
    private CacheManager cacheManager;

    public Optional<UserScenarioDAO> getUserScenario(final String userUniqueId) {
        if (userUniqueId == null) {
            log.error("User id is null");
            return Optional.empty();
        }
        return ofNullable(cacheManager.getCache(USER_SCENARIO_CACHE_NAME))
                .map(cache -> cache.get(userUniqueId, UserScenarioDAO.class));
    }

    public Optional<UserScenarioDAO> updateUserScenario(UserScenarioDAO userScenarioData) {
        if (userScenarioData == null) {
            log.error("Cache is null");
            return Optional.empty();
        }
        if (userScenarioData.getUserUniqueId() == null) {
            log.error("User id is null");
            return Optional.empty();
        }
        AtomicReference<UserScenarioDAO> success = new AtomicReference<>(userScenarioData);
        ofNullable(cacheManager.getCache(USER_SCENARIO_CACHE_NAME))
                .ifPresent(cache -> {
                    UserScenarioDAO cachedData = cache.get(userScenarioData.getUserUniqueId(), UserScenarioDAO.class);
                    if (cachedData == null || cachedData.hashCode() != userScenarioData.hashCode()) {
                        cache.put(userScenarioData.getUserUniqueId(), userScenarioData);
                    } else {
                        success.set(cachedData);
                    }
                });
        return Optional.of(success.get());
    }

    public void deleteUserScenario(final String userUniqueId) {
        if (userUniqueId == null) {
            log.error("Cannot delete user's scenario data from cache with empty user id");
            return;
        }
        ofNullable(cacheManager.getCache(USER_SCENARIO_CACHE_NAME))
                .ifPresent(cache -> cache.evictIfPresent(userUniqueId));
    }
}
