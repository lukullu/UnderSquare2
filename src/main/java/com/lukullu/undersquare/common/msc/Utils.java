package com.lukullu.undersquare.common.msc;

public class Utils {

    public static <T extends Number> T clamp(T min, T max, T value){

        return (min.doubleValue() > value.doubleValue()
                ? min
                : (max.doubleValue() < value.doubleValue()
                ? max
                : value));

    }

}
