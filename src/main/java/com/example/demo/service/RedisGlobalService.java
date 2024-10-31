package com.example.demo.service;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisGlobalService {
    public final RedisSortedSet sortedSetCollection;
    public final RedisString stringCollection;
    public final RedisList listCollection;
    public final RedisHashes redisHashes;
    public final RedisBitsService redisBits;
    public final RedisStrategyService redisStrategy;
}
