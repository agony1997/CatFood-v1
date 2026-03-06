package com.example.catfood.domain.common.type;

public enum ProductType {
    WET_FOOD("罐頭"),
    KIBBLE("飼料"),
    SAND("貓砂");

    public final String text;

    ProductType(String text) {
        this.text = text;
    }
}
