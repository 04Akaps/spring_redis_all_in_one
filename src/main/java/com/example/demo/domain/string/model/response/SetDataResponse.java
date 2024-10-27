package com.example.demo.domain.string.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

import com.example.demo.domain.string.model.SetModel;

@Schema(description = "redis collection set response")
public record SetDataResponse(
    @Schema(description = "set model response ")
    List<SetModel> response
) {
}
