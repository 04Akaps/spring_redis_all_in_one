package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.common.redis.RedisService;
import com.example.demo.domain.hashes.model.HashModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisStrategyService {
    final private RedisService redis;

    
}
