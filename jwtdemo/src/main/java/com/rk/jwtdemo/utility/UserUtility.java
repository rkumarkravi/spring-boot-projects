package com.rk.jwtdemo.utility;

public class UserUtility {
    public static String getOTP(){
        double random = Math.random();
        int convertToSixDig= (int) (random*1000000+1);
        return String.valueOf(convertToSixDig);
    }
}
