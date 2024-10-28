package com.example.demo.common.redis;

import org.springframework.stereotype.Service;

import com.example.demo.domain.strategy.model.ValueWithTTL;
import com.google.gson.Gson;

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisTemplate<String, String> template;

    @Value("${spring.redis.default-time}")
    private Duration defaultExpireTime;
    
    private final Gson gson;

    /**
     * ========================
     * Redis Data Management
     * ========================
     * This class provides methods to manage data in Redis using
     * JSON serialization and deserialization. It supports operations
     * such as setting, getting, and manipulating data with optional
     * expiration times.
     * 
     * Methods:
     * - getData: Retrieve an object from Redis based on its key.
     * - setData: Store an object in Redis with a specified expiration time.
     * - setData: Store an object in Redis with a default expiration time.
     * - multiSetData: Store multiple objects in Redis at once.
     * - setIfAbsent: Set a key only if it does not already exist.
     * - setIfPresent: Set a key only if it already exists.
     * ========================
    */

    public <T> T getData(String key, Class<T> valueType) {
        String jsonValue = template.opsForValue().get(key);
        if (jsonValue == null) {
            return null;
        }

        return gson.fromJson(jsonValue, valueType);
    }

    public <T> void setData(String key, T value, Duration expireTime) {
        String jsonValue = gson.toJson(value);
        template.opsForValue().set(key, jsonValue);
        template.expire(key, expireTime);
    }

    public <T> void setData(String key, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForValue().set(key, jsonValue);
        template.expire(key, this.defaultExpireTime);
    }

    public <T> void multiSetData(Map<String, T> dataMap) {
        Map<String, String> jsonMap = new HashMap<>();
        for (Map.Entry<String, T> entry : dataMap.entrySet()) {
            jsonMap.put(entry.getKey(), gson.toJson(entry.getValue()));
        }
        template.opsForValue().multiSet(jsonMap);
    }

    public <T> void setIfAbsent(String key, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForValue().setIfAbsent(key, jsonValue);
        template.expire(key, this.defaultExpireTime);
    }

    public <T> void setIfPresent(String key, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForValue().setIfPresent(key, jsonValue);
        template.expire(key, this.defaultExpireTime);
    }

    /**
     * ========================
     * Sorted Set Operations
     * ========================
     * This class provides methods to manage and interact with 
     * Sorted Sets in Redis, including adding, retrieving, and 
     * removing elements based on scores.
     * 
     * Methods:
     * - addToSortedSet: Add an element to a sorted set with a specified score.
     * - rangeByScore: Retrieve elements within a specified score range.
     * - removeFromSortedSet: Remove a specific element from a sorted set.
     * - getAllFromSortedSet: Get all elements from a sorted set in ascending order.
     * - getAllFromSortedSetDesc: Get all elements from a sorted set in descending order.
     * - getTopNFromSortedSet: Retrieve the top N elements from a sorted set.
     * ========================
    */

    public <T> void addToSortedSet(String key, T value, Float score) {
        String jsonValue = gson.toJson(value);
        template.opsForZSet().add(key, jsonValue, score);
    }

    public <T> Set<T> rangeByScore(String key, Float minScore, Float maxScore, Class<T> clazz) {
        Set<String> jsonValues = template.opsForZSet().rangeByScore(key, minScore, maxScore);
        Set<T> resultSet = new HashSet<>();

        if (jsonValues != null) {
            for (String jsonValue : jsonValues) {
                T value = gson.fromJson(jsonValue, clazz);
                resultSet.add(value);
            }
        }

        return resultSet;
    }

    public <T> void removeFromSortedSet(String key, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForZSet().remove(key, jsonValue);
    }

    public <T> Set<T> getAllFromSortedSet(String key, Class<T> clazz) {
        Set<String> jsonValues = template.opsForZSet().range(key, 0, -1);
        Set<T> resultSet = new HashSet<>();

        if (jsonValues != null) {
            for (String jsonValue : jsonValues) {
                T value = gson.fromJson(jsonValue, clazz);
                resultSet.add(value);
            }
        }

        return resultSet;
    }

    public <T> Set<T> getAllFromSortedSetDesc(String key, Class<T> clazz) {
        Set<String> jsonValues = template.opsForZSet().reverseRange(key, 0, -1);
        Set<T> resultSet = new HashSet<>();

        if (jsonValues != null) {
            for (String jsonValue : jsonValues) {
                T value = gson.fromJson(jsonValue, clazz);
                resultSet.add(value);
            }
        }

        return resultSet;
    }

    public <T> Set<T> getTopNFromSortedSet(String key, int n, Class<T> clazz) {
        Set<String> jsonValues = template.opsForZSet().reverseRange(key, 0, n - 1);
        Set<T> resultSet = new HashSet<>();

        if (jsonValues != null) {
            for (String jsonValue : jsonValues) {
                T value = gson.fromJson(jsonValue, clazz);
                resultSet.add(value);
            }
        }

        return resultSet;
    }

    /**
     * ========================
     * Redis List Operations
     * ========================
     * This class provides methods to manage lists in Redis. It supports
     * operations for adding, retrieving, and removing elements from lists.
     * 
     * Methods:
     * - addToListLeft: Add an element to the left end of the list.
     * - addToListRight: Add an element to the right end of the list.
     * - getList: Retrieve all elements from the list.
     * - removeFromList: Remove a specific element from the list.
     * ========================
    */

    public <T> void addToListLeft(String key, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForList().leftPush(key, jsonValue);
    }

    public <T> void addToListRight(String key, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForList().rightPush(key, jsonValue);
    }

    public <T> List<T> getAllList(String key, Class<T> clazz) {
        List<String> jsonValues = template.opsForList().range(key, 0, -1);
        List<T> resultSet = new ArrayList<>();

        if (jsonValues != null) {
            for (String jsonValue : jsonValues) {
                T value = gson.fromJson(jsonValue, clazz);
                resultSet.add(value);
            }
        }

        return resultSet;
    }

    public <T> void removeFromList(String key, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForList().remove(key, 1, jsonValue);
    }

    /**
     * ========================
     * Redis Set Operations
     * ========================
     * This class provides methods to manage sets in Redis. It supports
     * operations for adding, retrieving, and removing elements from sets.
     * 
     * Methods:
     * - addToSet: Add an element to the set.
     * - getSet: Retrieve all elements from the set.
     * - removeFromSet: Remove a specific element from the set.
     * ========================
    */

    public <T> void addToSet(String key, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForSet().add(key, jsonValue);
    }

    public <T> Set<T> getSet(String key, Class<T> clazz) {
        Set<String> jsonValues = template.opsForSet().members(key);
        Set<T> resultSet = new HashSet<>();

        if (jsonValues != null) {
            for (String jsonValue : jsonValues) {
                T v = gson.fromJson(jsonValue, clazz);
                resultSet.add(v);
            }
        }

        return resultSet;
    }

    public <T> void removeFromSet(String key, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForSet().remove(key, jsonValue);
    }

    /**
     * ========================
     * Redis Hash Operations
     * ========================
     * This class provides methods to manage hashes in Redis. It supports
     * operations for adding, retrieving, and removing fields within a hash.
     * 
     * Methods:
     * - putInHash: Add or update a field in the hash.
     * - getFromHash: Retrieve the value of a specific field from the hash.
     * - removeFromHash: Remove a specific field from the hash.
     * ========================
    */

    public <T> void putInHash(String key, String field, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForHash().put(key, field, jsonValue);
    }

    public <T> T getFromHash(String key, String field, Class<T> clazz) {
        Object result = template.opsForHash().get(key, field);

        if (result != null) {
            return clazz.cast(result);
        }

        return null;
    }

    public void removeFromHash(String key, String field) {
        template.opsForHash().delete(key, field);
    }

    /**
     * ========================
     * Redis Bit Operations
     * ========================
     * This class provides methods to manage bitmaps in Redis. It supports
     * operations for setting and getting individual bits.
     * 
     * Methods:
     * - setBit: Set a specific bit at a given offset in the bitmap.
     * - getBit: Retrieve the value of a specific bit at a given offset.
     * ========================
    */

    public void setBit(String key, long offset, boolean value) {
        template.opsForValue().setBit(key, offset, value);
    }

    public boolean getBit(String key, long offset) {
        return template.opsForValue().getBit(key, offset);
    }


    /**
     * ========================
     * Redis Value and TTL Retrieval
     * ========================
     * This method retrieves the value associated with a given key from Redis 
     * along with its time-to-live (TTL). It uses Redis pipelining to efficiently 
     * fetch both the value and the TTL in a single operation.
     * 
     * Generic Type:
     * - T: The type of the object to be deserialized from the JSON stored in Redis.
     * 
     * Parameters:
     * - key: The key whose associated value and TTL are to be retrieved.
     * - clazz: The Class object of the type T, used for deserialization of the JSON value.
     * 
     * Returns:
     * - A ValueWithTTL<T> object containing the retrieved value and its TTL.
     *   - value: The deserialized object retrieved from Redis.
     *   - ttl: The remaining time-to-live of the key in seconds; null if the key does not exist.
     * 
     * Exceptions:
     * - Throws exceptions related to Redis operations and JSON parsing. 
     *   These should be handled by the calling code.
     * ========================
     */
    public <T> ValueWithTTL<T> GetValueWithTTL(String key, Class<T> clazz) {
        T value = null;
        Long ttl = null;

        try {
            List<Object> results = template.executePipelined((RedisCallback<Object>) connection -> {
                connection.openPipeline();

                StringRedisConnection conn = (StringRedisConnection) connection;

                conn.get(key); // 값 가져오기
                conn.pTtl(key); // TTL 가져오기

                connection.closePipeline();
                return null;
            });

            value = (T) gson.fromJson((String) results.get(0), clazz); // JSON을 객체로 변환
            ttl = (Long) results.get(1); // TTL 가져오기
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        return new ValueWithTTL<>(value, ttl);
    }


    /**
     * ========================
     * Redis Key Summation and Renewal
     * ========================
     * This method executes a Lua script to sum the values of two keys 
     * and store the result in a specified key atomically.
     * 
     * Parameters:
     * - script: The Lua script containing the sum logic.
     * - key1: The first key to sum.
     * - key2: The second key to sum.
     * - resultKey: The key where the result will be stored.
     * 
     * Returns:
     * - A Long representing the sum of the values from key1 and key2.
     * ========================
     */
    public Long SumTwoKeyAndRenew(String script, String key1, String key2, String resultKey) {
    
        return template.execute((RedisCallback<Long>) connection -> {

            byte[] scriptBytes = script.getBytes();
            byte[] key1Bytes = key1.getBytes();
            byte[] key2Bytes = key2.getBytes();
            byte[] resultKeyBytes = resultKey.getBytes();
   
            return (Long) connection.execute("EVAL", 
                    scriptBytes, 
                    key1Bytes, 
                    key2Bytes, 
                    resultKeyBytes);
        });
    }
}