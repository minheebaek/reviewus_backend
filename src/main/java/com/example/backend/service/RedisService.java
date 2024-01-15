package com.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * getData
     *
     * @parm key
     * @return String
     */
    public String getData(String key) {    // key를 통해 value 리턴
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * setDataExpire
     *
     * @parm key
     * @parm value
     * @parm duration
     * @return void
     */
    public void setDataExpire(String key, String value, long duration) {// 유효 시간 동안(key, value)저장
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    /**
     * deleteData
     *
     * @parm key
     * @return void
     */
    public void deleteData(String key) {
        stringRedisTemplate.delete(key);
    }
}