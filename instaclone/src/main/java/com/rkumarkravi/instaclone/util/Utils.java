package com.rkumarkravi.instaclone.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class Utils {
    public static Gson gson=new Gson();

    public static boolean isJson(String json) {
        try {
            Object jsonObjType = gson.fromJson(json, Object.class).getClass();
            if(jsonObjType.equals(String.class)){
                return false;
            }else {
                return true;
            }
        } catch (Exception ex) {
          return false;
        }
    }
}
