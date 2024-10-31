package com.example.demo.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.common.redis.RedisService;
import com.example.demo.domain.hashes.model.HashModel;
import com.example.demo.domain.strategy.model.ValueWithTTL;
import com.example.demo.domain.string.model.SetModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisStrategyService {
    final private RedisService redis;

    public SetModel SimpleStrategy(String key) {
        SetModel model = redis.getData(key, SetModel.class);
    
        if (model == null) {
            // DB 조회하여서 가져온 데이터라고 가정
            SetModel fromDBData = new SetModel(key, "db name");

            redis.setData(key, fromDBData);

            return fromDBData;
        } else {
            return model;
        }
    }

    public SetModel PERStrategy(String key) {
        ValueWithTTL<SetModel> valueWithTTL = redis.GetValueWithTTL(key, SetModel.class);

        if (valueWithTTL != null) {
            // null이 아니라면, PER 알고리즘을 적용하여 값을 계산
            AsyncPERStrategy(key, valueWithTTL);

            return valueWithTTL.getValue();
        } else {
              // DB 조회하여서 가져온 데이터라고 가정
            SetModel fromDBData = new SetModel(key, "db name");

            redis.setData(key, fromDBData);

            return fromDBData;
        }
    }

    @Async
    public void AsyncPERStrategy(String key, ValueWithTTL<SetModel> v) {
        Long remainTTL = v.getTTL();

        double updateProbability = calculateUpdateProbability(remainTTL);

        // 랜덤 값 생성 및 업데이트 여부 결정
        Random random = new Random();

         if (random.nextDouble() < updateProbability) {
            // DB 조회하여서 가져온 데이터라고 가정
            SetModel fromDBData = new SetModel(key, "db name");
            redis.setData(key, fromDBData);
        }
    }

    private double calculateUpdateProbability(Long remainTTL) {
        // PER 알고리즘을 사용
        // remainTTL이 짧을수록 업데이트 확률이 높아지는 확률적 캐싱 업데이트 방법
        double baseProbability = 0.5; // 기본 확률
        double decayRate = 0.1; // 감쇠율
        return baseProbability * Math.pow(Math.E, -decayRate * remainTTL);
    }



    
}
