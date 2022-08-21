package com.rk.animestream.utils;

public class Utils {
    public static String getOTP(){
        double random = Math.random();
        int convertToSixDig= (int) (random*1000000+1);
        return String.valueOf(convertToSixDig);
    }
}
