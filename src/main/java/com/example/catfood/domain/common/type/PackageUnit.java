package com.example.catfood.domain.common.type;

public enum PackageUnit {
    BAG("袋"),
    CAN("罐"),
    BOX("箱");

    public final String text;

    PackageUnit(String text) {
        this.text = text;
    }
}
