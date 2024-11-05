package com.example.demo.domain.sortedSet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.sortedSet.model.SortedSet;
import com.example.demo.domain.sortedSet.model.request.SortedSetReqeust;
import com.example.demo.service.RedisGlobalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Tag(name = "가장 간단한 Sorted Set", description = "가장 Sorted Set에 관련된 부분을 담당합니다.")
@RestController
@RequestMapping("/api/v1/sorted-set")
@RequiredArgsConstructor
public class SortedSetController {
    private final RedisGlobalService service;


    @Operation(
        summary = "단순한 sorted set 데이터 설정"
    )
    @PostMapping("/sorted-set-collection")
    public void SetSortedSet(
        @RequestBody @Valid SortedSetReqeust request 
    ) {
        service.sortedSetCollection.SetSortedSet(request);
    }

    @Operation(
        summary = "범위에 따른 Sorted Set 데이터를 조회"
    )
    @GetMapping("/get-sorted-set-by-range")
    public Set<SortedSet> GetSortedSet(
        @RequestParam @Valid String key,
        @RequestParam @Valid Float minScore,
        @RequestParam @Valid Float maxScore
    ) {
        return service.sortedSetCollection.GetSetDataByRange(key, minScore, maxScore);
    }

    
    @Operation(
        summary = "특정 값을 Sorted Set에서 제거"
    )
    @PostMapping("/remove-sorted-set")
    public void RemoveSortedSet(
        @RequestBody @Valid SortedSetReqeust request 
    ){
        service.sortedSetCollection.RemoveFromSortedSet(request);
    }


    @Operation(
        summary = "상위 n개를 조회합니다."
    )
    @GetMapping("/get-sorted-set-by-top")
    public List<SortedSet> GetTopData(
        @RequestParam @Valid String key,
        @RequestParam @Valid Integer n
    ) {
        return service.sortedSetCollection.GetTopN(key, n);
    }
    


}
