package com.senel.weather.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachConfig {
    public CacheManager cacheManager(){
        return new ConcurrentMapCacheManager("weather");
    }
}
