package ru.smartapp.core.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConfigTest {
    @Autowired
    ReactiveWebServerFactory webServerFactory;

    @Test
    void reactiveWebServerFactory() {
        assertNotNull(webServerFactory);
    }
}