package com.gizasystems.PoC.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
public class RateLimiterService {


    private final RedisTemplate<String, Object> redisTemplate;


    public boolean isAllowed(String username, int limit) {
        String key = "rate_limit_" + username;
        System.out.println(redisTemplate.opsForValue().get(key));
        String currentCount = (String) redisTemplate.opsForValue().get(key);

        if (currentCount == null) {
            redisTemplate.opsForValue().set(key, 1, 1, TimeUnit.MINUTES);
            return true;
        } else if (Integer.parseInt(currentCount) < limit) {
            redisTemplate.opsForValue().increment(key);
            return true;
        } else {
            return false;
        }
    }
}

