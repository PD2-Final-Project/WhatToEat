package com.whattoeat.model.api;

public enum Places {
    RESTAURANT(0),
    CAFE(1),
    SHOPPING_MALL(2),
    CONVENIENCE_STORE(3),
    BAR(4),
    STORE(5),
    LIQUOR_STORE(6);

    private final int value;
    private Places(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
}
