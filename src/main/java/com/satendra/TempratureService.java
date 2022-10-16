package com.satendra;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
public class TempratureService {

	// @Autowired
	// private RedisTemplate<String, Object> template;

	@Autowired
	private StringRedisTemplate redisTemplate;

	public void insert( TemperatureData temperatureData) {

		String uuid = UUID.randomUUID().toString();
		ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.systemDefault()).withNano(0);

		temperatureData.setUuid(uuid);
		temperatureData.setDateTime(zonedDateTime.toString());
		temperatureData.setTimestamp(String.valueOf(zonedDateTime.toInstant().toEpochMilli()));

		HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
		opsForHash.put("123456#" + uuid, "uuid", temperatureData.getUuid());
		opsForHash.put("123456#" + uuid, "weather", temperatureData.getWeather());
		opsForHash.put("123456#" + uuid, "degree", temperatureData.getDegree());
		opsForHash.put("123456#" + uuid, "dateTime", temperatureData.getDateTime());
		opsForHash.put("123456#" + uuid, "timestamp", temperatureData.getTimestamp());


		ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();

		opsForZSet.add("123456", "123456#" + uuid, zonedDateTime.toInstant().toEpochMilli());

	}

}
