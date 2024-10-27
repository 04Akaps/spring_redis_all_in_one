package com.example.demo.domain.bits.model.request;

import com.example.demo.common.request.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "redis collection set request")
public record BitsRequest(
    BaseRequest baseRequest,

    @Schema(description = "offset")
    @NotBlank
    @NotNull
    Long offset,
    
    @Schema(description = "bool")
    @NotBlank
    @NotNull
    boolean bool
) {
}
