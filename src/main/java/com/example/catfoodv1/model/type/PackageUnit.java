package com.example.catfoodv1.model.type;

/**
 * 商品包裝單位
 */
public enum PackageUnit {
    BAG("袋"),
    CAN("罐"),
    BOX("箱"),
    KG("公斤"),
    G("公克");

    public final String text;

    PackageUnit(String text) {
        this.text = text;
    }

}