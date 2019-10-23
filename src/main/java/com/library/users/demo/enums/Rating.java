package com.library.users.demo.enums;

import java.util.HashMap;
import java.util.Map;

public enum Rating {
    ONE (1d),
    TWO (2d),
    THREE (3d),
    FOUR (4d),
    FIVE (5d);

    private Double value;
    private static Map map = new HashMap<>();

    private Rating(Double value) {
        this.value = value;
    }

    static {
        for (Rating rating : Rating.values()) {
            map.put(rating.value, rating);
        }
    }

    public static Rating valueOf(int rating) {
        return (Rating) map.get(rating);
    }

    public Double getValue() {
        return value;
    }

    /*PageType pageType = PageType.valueOf(2); // pageType = PageType.CODING

    ProductType productType = ProductType.DATABASES;
    int productTypeId = productType.getValue(); // productTypeId = 3*/
}
