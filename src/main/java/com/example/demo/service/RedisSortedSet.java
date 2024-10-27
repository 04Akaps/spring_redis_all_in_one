package com.example.demo.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.example.demo.common.redis.RedisService;
import com.example.demo.domain.sortedSet.model.SortedSet;
import com.example.demo.domain.sortedSet.model.request.SortedSetReqeust;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisSortedSet {
    private final RedisService redis;

    public void SetSortedSet(SortedSetReqeust reqeust) {
        SortedSet data = new SortedSet(reqeust.Name(), reqeust.Score());
        redis.addToSortedSet(reqeust.baseRequest().Key(), data, data.getScore());
    }

    public Set<SortedSet> GetSetDataByRange(String key, Float min, Float max) {
        return redis.rangeByScore(key, min, max, SortedSet.class);
    }

    public void RemoveFromSortedSet(SortedSetReqeust reqeust) {
        SortedSet data = new SortedSet(reqeust.Name(), reqeust.Score());
        redis.removeFromSortedSet(reqeust.baseRequest().Key(), data);
    }

    public Set<SortedSet> GetTopN(String key, Integer n) {
        return redis.getTopNFromSortedSet(key, n, SortedSet.class);
    }
}
