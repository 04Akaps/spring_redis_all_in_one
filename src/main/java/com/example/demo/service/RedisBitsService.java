package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.common.redis.RedisService;
import com.example.demo.domain.hashes.model.HashModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisBitsService {
    final private RedisService redis;

    public void SetBit(String key, long offset, boolean v) {
        redis.setBit(key, offset, v);
    }

    public boolean GetBit(String key, long offset) {
        return redis.getBit(key, offset);
    }
}
