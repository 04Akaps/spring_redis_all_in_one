package com.example.demo.domain.hashes.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.hashes.model.HashModel;
import com.example.demo.domain.hashes.model.request.HashRequest;
import com.example.demo.service.RedisGlobalService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "가장 간단한 Hashes", description = "가장 간단한 Hash 타입을 담당합니다.")
@RestController
@RequestMapping("/api/v1/hash")
@RequiredArgsConstructor
public class HashesController {
    private final RedisGlobalService service;

    @Operation(
        summary = "put hashes"
    )
    @PostMapping("/put-hashes")
    public void PutHashes(
        @RequestBody @Valid HashRequest request
    ) {
        service.redisHashes.PutInHash(request.baseRequest().Key(), request.Field(), request.Name());
    }

    @Operation(
        summary = "get hashes"
    )
    @GetMapping("/get-hash-value")
    public HashModel GetHashes(
        @RequestParam @Valid String key,
        @RequestParam @Valid String field
    )  {
        return service.redisHashes.GetFromHash(key, field);
    }

    @Operation(
        summary = "remove"
    )
    @PostMapping("/remove")
    public void RemoveValue(
        @RequestBody @Valid HashRequest request
    ) {
        service.redisHashes.RemoveFrimHash(request.baseRequest().Key(), request.Field(), request.Name());
    }
}
