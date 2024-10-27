package com.example.demo.domain.list.domain;

import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.list.model.ListModel;
import com.example.demo.domain.list.model.request.ListRequest;
import com.example.demo.domain.string.model.request.MultiSetRequest;
import com.example.demo.service.RedisGlobalService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "가장 간단한 List", description = "가장 간단한 List 타입을 담당합니다.")
@RestController
@RequestMapping("/api/v1/list")
@RequiredArgsConstructor
public class ListController {
    private final RedisGlobalService service;

    @Operation(
        summary = "list add to left"
    )
    @PostMapping("/list-add-left")
    public void SetNewValueToLeft(
        @RequestBody @Valid ListRequest request
    ) {
        service.listCollection.AddToListLeft(request.baseRequest().Key(), request.Name());
    }

    @Operation(
        summary = "list add to right"
    )
    @PostMapping("/list-add-right")
    public void SetNewValueToRight(
        @RequestBody @Valid ListRequest request
    ) {
        service.listCollection.AddToListRight(request.baseRequest().Key(), request.Name());
    }

    @Operation(
        summary = "all"
    )
    @GetMapping("/all")
    public List<ListModel> GetAllList(
        @RequestParam @Valid String key
    ) {
        return service.listCollection.GetAllData(key);
    }

    @Operation(
        summary = "remove"
    )
    @PostMapping("/remove")
    public void RemoveValue(
        @RequestBody @Valid ListRequest request
    ) {
        service.listCollection.RemoveList(request.baseRequest().Key(), request.Name());
    }
}
