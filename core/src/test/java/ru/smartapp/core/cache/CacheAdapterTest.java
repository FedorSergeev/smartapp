package ru.smartapp.core.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import ru.smartapp.core.common.dao.UserScenarioDAO;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static ru.smartapp.core.config.Config.USER_SCENARIO_CACHE_NAME;

@SpringBootTest
public class CacheAdapterTest {
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private CacheAdapter cacheAdapter;

    @BeforeEach
    @AfterEach
    public void clear() {
        cacheManager.getCache(USER_SCENARIO_CACHE_NAME).invalidate();
    }

    @Test
    public void testGetByNull() {
        Optional<UserScenarioDAO> nullCache = cacheAdapter.getUserScenario(null);
        assertFalse(nullCache.isPresent());
    }

    @Test
    public void testGetByAbsentId() {
        Optional<UserScenarioDAO> absentCache = cacheAdapter.getUserScenario(UUID.randomUUID().toString());
        assertFalse(absentCache.isPresent());
    }

    @Test
    public void testGetById() {
        String id = UUID.randomUUID().toString();
        String stateId = UUID.randomUUID().toString();
        String intent = UUID.randomUUID().toString();
        UserScenarioDAO userScenarioDAO = new UserScenarioDAO();
        userScenarioDAO.setUserUniqueId(id);
        userScenarioDAO.setStateId(stateId);
        userScenarioDAO.setIntent(intent);
        cacheAdapter.updateUserScenario(userScenarioDAO);

        Optional<UserScenarioDAO> optional = cacheAdapter.getUserScenario(id);
        assertTrue(optional.isPresent());
        UserScenarioDAO dao = optional.get();
        assertEquals(id, dao.getUserUniqueId());
        assertEquals(stateId, dao.getStateId());
        assertEquals(intent, dao.getIntent());
        assertNull(dao.getScenarioData());
    }

    @Test
    public void testUpdateNull() {
        Optional<UserScenarioDAO> optional = cacheAdapter.updateUserScenario(null);
        assertFalse(optional.isPresent());
    }

    @Test
    public void testUpdateWithNullId() {
        Optional<UserScenarioDAO> optional = cacheAdapter.updateUserScenario(new UserScenarioDAO());
        assertFalse(optional.isPresent());
    }

    @Test
    public void testUpdateWithNullParams() {
        String id = UUID.randomUUID().toString();
        UserScenarioDAO userScenarioDAO = new UserScenarioDAO();
        userScenarioDAO.setUserUniqueId(id);
        Optional<UserScenarioDAO> optional = cacheAdapter.updateUserScenario(userScenarioDAO);
        assertTrue(optional.isPresent());
        assertEquals(id, optional.get().getUserUniqueId());
    }

    @Test
    public void testUpdate() {
        String id = UUID.randomUUID().toString();
        String stateId = UUID.randomUUID().toString();
        String intent = UUID.randomUUID().toString();
        UserScenarioDAO userScenarioDAO = new UserScenarioDAO();
        userScenarioDAO.setUserUniqueId(id);
        userScenarioDAO.setStateId(stateId);
        userScenarioDAO.setIntent(intent);
        Optional<UserScenarioDAO> optional = cacheAdapter.updateUserScenario(userScenarioDAO);
        assertTrue(optional.isPresent());
        UserScenarioDAO dao = optional.get();
        assertNotNull(dao);
        assertEquals(id, dao.getUserUniqueId());
        assertEquals(stateId, dao.getStateId());
        assertEquals(intent, dao.getIntent());
        assertNull(dao.getScenarioData());
    }

    @Test
    public void testDeleteByNull() {
        cacheAdapter.deleteUserScenario(null);
    }

    @Test
    public void testDeleteByAbsentId() {
        cacheAdapter.deleteUserScenario(UUID.randomUUID().toString());
    }

    @Test
    public void testDeleteById() {
        String id = UUID.randomUUID().toString();
        UserScenarioDAO userScenarioDAO = new UserScenarioDAO();
        userScenarioDAO.setUserUniqueId(id);
        Optional<UserScenarioDAO> optional = cacheAdapter.updateUserScenario(userScenarioDAO);
        assertTrue(optional.isPresent());
        optional = cacheAdapter.getUserScenario(id);
        assertTrue(optional.isPresent());
        cacheAdapter.deleteUserScenario(id);
        optional = cacheAdapter.getUserScenario(id);
        assertFalse(optional.isPresent());
    }
}
