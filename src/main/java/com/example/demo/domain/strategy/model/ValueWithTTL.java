package com.example.demo.domain.strategy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValueWithTTL<T> {
    T Value;
    Long TTL;
}
