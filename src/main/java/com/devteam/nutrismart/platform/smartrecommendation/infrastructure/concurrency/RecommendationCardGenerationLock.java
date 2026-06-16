package com.devteam.nutrismart.platform.smartrecommendation.infrastructure.concurrency;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RecommendationCardGenerationLock {

    private final Set<String> activeLocks = ConcurrentHashMap.newKeySet();

    public boolean tryAcquire(String key) {
        return activeLocks.add(key);
    }

    public void release(String key) {
        activeLocks.remove(key);
    }
}
