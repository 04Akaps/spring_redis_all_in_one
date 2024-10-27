package com.example.demo.domain.string.model.request;

import com.example.demo.common.request.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "redis collection set request")
public record MultiSetRequest(
    BaseRequest baseRequest,
    
    @Schema(description = "names")
    @NotBlank
    @NotNull
    String[] Names
) {
}
