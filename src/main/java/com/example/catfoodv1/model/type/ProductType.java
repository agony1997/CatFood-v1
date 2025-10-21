package com.example.catfoodv1.model.type;

public enum ProductType {
    WET_FOOD("罐頭"),
    KIBBLE("飼料");

    final String text;

    ProductType(String text) {
        this.text = text;
    }

}
