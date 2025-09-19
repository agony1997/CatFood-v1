package com.example.catfoodv1.model.type;

public enum WeightUnit {
    KG("公斤"),
    POUND("磅"),
    G("公克");

    public final String text;

    WeightUnit(String text) {
        this.text = text;
    }

}
