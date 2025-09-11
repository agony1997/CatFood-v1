package com.example.catfoodv1.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SelectType {
    BRAND("品牌"),
    COMPANY("公司"),
    TAG("標籤"),
    STORE("販售處"),
    INGREDIENT("主要肉底");

    private final String label;
}