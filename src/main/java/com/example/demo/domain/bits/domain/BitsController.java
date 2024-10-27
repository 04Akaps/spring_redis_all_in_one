package com.example.demo.domain.bits.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.bits.model.request.BitsRequest;
import com.example.demo.domain.hashes.model.HashModel;
import com.example.demo.domain.hashes.model.request.HashRequest;
import com.example.demo.service.RedisGlobalService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "가장 간단한 Bits", description = "가장 간단한 Bits 타입을 담당합니다.")
@RestController
@RequestMapping("/api/v1/bits")
@RequiredArgsConstructor
public class BitsController {
    private final RedisGlobalService service;

    @Operation(
        summary = "put bits"
    )
    @PostMapping("/put-bits")
    public void PutBits(
        @RequestBody @Valid BitsRequest request
    ) {
        service.redisBits.SetBit(request.baseRequest().Key(), request.offset(), request.bool());
    }

    @Operation(
        summary = "get bits"
    )
    @GetMapping("/get-bits-value")
    public boolean GetBits(
        @RequestParam @Valid String key,
        @RequestParam @Valid Long offset
    )  {
        return service.redisBits.GetBit(key, offset);
    }
}
