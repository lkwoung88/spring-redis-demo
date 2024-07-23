package com.example.redisclusterdemo.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void save(String something) {
        try {
            String uuid = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(uuid, something);
        } catch (Exception e) {
            log.info(e.toString());
            log.info(e.getMessage());
        }
    }

    public List<String> getList() {
        List<String> values = new ArrayList<>();
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        // Using SCAN command to get all keys
        ScanOptions scanOptions = ScanOptions.scanOptions().count(Long.MAX_VALUE).match("*").build();
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(scanOptions);

        while (cursor.hasNext()) {
            String key = new String(cursor.next());
            String value = ops.get(key);
            values.add(value);
        }

        return values;
    }
}
