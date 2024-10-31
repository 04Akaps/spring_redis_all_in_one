package com.example.demo.domain.strategy.domain;

import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.list.model.ListModel;
import com.example.demo.domain.list.model.request.ListRequest;
import com.example.demo.domain.sortedSet.model.request.SortedSetReqeust;
import com.example.demo.domain.string.model.SetModel;
import com.example.demo.domain.string.model.request.MultiSetRequest;
import com.example.demo.service.RedisGlobalService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "가장 간단한 Strategy", description = "가장 간단한 Stragety 타입을 담당합니다.")
@RestController
@RequestMapping("/api/v1/strategy")
@RequiredArgsConstructor
public class StrategyController {
    private final RedisGlobalService service;

    @Operation(
        summary = "가장 단순하게 많이 사용되는 strategy"
    )
    @GetMapping("/simple-strategy")
    public SetModel SimpleStrategy(
        @RequestParam @Valid String key
    ) {
        return service.redisStrategy.SimpleStrategy(key);
    }

    @Operation(
        summary = "per 알고리즘을 사용한 Strategy"
    )
    @GetMapping("/per-strategy") 
    public SetModel PERStrategy(
        @RequestParam @Valid String key
    ) {
        return service.redisStrategy.PERStrategy(key);
    }

}
