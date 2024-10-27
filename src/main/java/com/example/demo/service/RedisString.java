package com.example.demo.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.example.demo.common.redis.RedisService;
import com.example.demo.domain.string.model.SetModel;
import com.example.demo.domain.string.model.request.MultiSetRequest;
import com.example.demo.domain.string.model.request.SetDataRequest;
import com.example.demo.domain.string.model.response.SetDataResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisString {
    private final RedisService redis;

    public void Set(SetDataRequest req) {
        redis.setData(req.baseRequest().Key(), null);
    }

    public SetDataResponse Get(SetDataRequest req) {
        SetModel result = redis.getData(req.baseRequest().Key(), SetModel.class);

        List<SetModel> res = new ArrayList<SetModel>();
        res.add(result);

        return new SetDataResponse(res);
    }

    public void MultiSet(MultiSetRequest request) {

        Map<String, Object> dataMap = new HashMap<>();


        for (int i = 0; i < request.Names().length; i++) {
            String name = request.Names()[i];
            // SetModel 객체 생성
            SetModel setModel = new SetModel("key:" + (i + 1), name);
            // dataMap에 추가
            dataMap.put("key:" + (i + 1), setModel);
        }

        redis.multiSetData(dataMap);
    }
}
