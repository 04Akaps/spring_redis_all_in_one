package com.example.demo.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.example.demo.common.redis.RedisService;
import com.example.demo.domain.list.model.ListModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisList {
    final private RedisService redis;

    public void AddToListLeft(String key, String name) {
        ListModel data = new ListModel(name);
        redis.addToListLeft(key, data);
    }

    public void AddToListRight(String key, String name) {
        ListModel data = new ListModel(name);
        redis.addToListRight(key, data);
    }

    public List<ListModel> GetAllData(String key) {
        return redis.getAllList(key, ListModel.class);
    }

    public void RemoveList(String key, String name) {
        ListModel data = new ListModel(name);
        redis.removeFromList(key, data);
    }
}
