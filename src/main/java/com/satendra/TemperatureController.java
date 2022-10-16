package com.satendra;

import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TemperatureController {

    private final StringRedisTemplate redisTemplate;

    private final TemperatureService temperatureService;


    @GetMapping("/temp-all")
    public List<TemperatureData> getAllTemperature() {

        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
        Set<String> rangeByScore = opsForZSet.rangeByScore("123456", Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        return getMulti(rangeByScore);
    }

    @GetMapping("/temp")
    public List<TemperatureData> getTemperatureByRange(@RequestParam Double start, @RequestParam Double end) {

        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
        Set<String> rangeByScore = opsForZSet.rangeByScore("123456", start, end);
        return getMulti(rangeByScore);
    }

    @PostMapping("/temp")
    public void insertTemperature(@RequestBody TemperatureData temperature) {
        temperatureService.insert(temperature);

    }


    public List<TemperatureData> getMulti(Set<String> rangeByScore) {
        List<Object> objects = redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
            rangeByScore.forEach(stringRedisConn::hGetAll);
            return null;
        });
        return objects.stream().map(TemperatureData::new).collect(Collectors.toList());
    }


}
