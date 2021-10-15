package ru.smartapp.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import reactor.netty.resources.LoopResources;

import java.util.Collections;

@Configuration
public class Config {

    public static final  String USER_SCENARIO_CACHE_NAME = "user_cache_name";

    /**
     * Number of worker threads
     */
    @Value("${smartapp.server.resources.worker_count:16}")
    private int workerCount;

    @Bean("cacheManager")
    public CacheManager simpleCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ConcurrentMapCache contentCache = new ConcurrentMapCache(USER_SCENARIO_CACHE_NAME, false);
        cacheManager.setCaches(Collections.singletonList(contentCache));
        return cacheManager;
    }


    /**
     * Customizes Netty server
     *
     * @return factory for creating the web server
     */
    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {
        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();

        ReactorResourceFactory resourceFactory = new ReactorResourceFactory();
        resourceFactory.setLoopResources(LoopResources.create("http", workerCount, true));
        factory.setResourceFactory(resourceFactory);

        return factory;
    }

}
