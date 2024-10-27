package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.common.redis.RedisService;
import com.example.demo.domain.hashes.model.HashModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisHashes {
    final private RedisService redis;

    public void PutInHash(String key, String field, String name) {
        HashModel model = new HashModel(name);
        redis.putInHash(key, field, model);
    }

    public HashModel GetFromHash(String key, String field) {
        HashModel res = redis.getFromHash(key, field, HashModel.class);
        return res;
    }

    public void RemoveFrimHash(String key, String field, String name) {
        HashModel model = new HashModel(name);
        redis.putInHash(key, field, model);
    }
}
