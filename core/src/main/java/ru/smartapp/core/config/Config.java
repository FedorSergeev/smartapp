package ru.smartapp.core.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Configuration
public class Config {

    public final static String USER_SCENARIO_CACHE_NAME = "user_cache_name";

    @Bean("cacheManager")
    public CacheManager simpleCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ConcurrentMapCache contentCache = new ConcurrentMapCache(USER_SCENARIO_CACHE_NAME, false);
        cacheManager.setCaches(Collections.singletonList(contentCache));
        return cacheManager;
    }

    @Bean
    public WebClient getWebClient() {
        return WebClient.create();
    }

}
