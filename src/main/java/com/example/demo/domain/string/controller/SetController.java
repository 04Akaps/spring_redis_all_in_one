package com.example.demo.domain.string.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.string.model.request.MultiSetRequest;
import com.example.demo.domain.string.model.request.SetDataRequest;
import com.example.demo.domain.string.model.response.SetDataResponse;
import com.example.demo.service.RedisGlobalService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "가장 간단한 String Set", description = "가장 간단한 String 타입의 Set을 담당합니다.")
@RestController
@RequestMapping("/api/v1/set")
@RequiredArgsConstructor
public class SetController {

    private final RedisGlobalService service;

    @Operation(
        summary = "단순한 key에 대한 String Set"
    )
    @PostMapping("/set-string-collection")
    public void SetNewValue(
        @RequestBody  @Valid SetDataRequest request
    ) {
        service.stringCollection.Set(request);
    }

    @Operation(
        summary = "단순한 key에 대한 String Get"
    )
    @GetMapping("/get-string-collection")
    public SetDataResponse GetSetValue(
        @RequestParam @Valid String key
    ) {
        return service.stringCollection.Get(key);
    }

    @Operation(
        summary = "String 타입에 대한 Bulk Set"
    )
    @PostMapping("/multi-set-collection")
    public void MultiSet(
        @RequestBody @Valid MultiSetRequest request
    ) {
        service.stringCollection.MultiSet(request);
    }
    
}
