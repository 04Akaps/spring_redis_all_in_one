package com.example.demo.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "base key reqeust")
public record BaseRequest(
    @Schema(description = "redis Key")
    @NotBlank
    @NotNull
    String Key
) {
}
