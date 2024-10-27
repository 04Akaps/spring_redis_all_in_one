package com.example.demo.domain.list.model.request;

import com.example.demo.common.request.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "redis list set request")
public record ListRequest(
    BaseRequest baseRequest,
    
    @Schema(description = "name")
    @NotBlank
    @NotNull
    String Name
) {
}
