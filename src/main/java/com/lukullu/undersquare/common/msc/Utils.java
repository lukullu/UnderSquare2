package com.lukullu.undersquare.common.msc;

import java.util.Base64;

public class Utils {

    public static <T extends Number> T clamp(T min, T max, T value){

        return (min.doubleValue() > value.doubleValue()
                ? min
                : (max.doubleValue() < value.doubleValue()
                ? max
                : value));

    }

}
