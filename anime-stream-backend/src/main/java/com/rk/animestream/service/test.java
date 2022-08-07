package com.rk.animestream.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class test {
    void checkFile(){
        Path filePathVideo = Paths.get("c://animestream/"+"videostore");
        if(!filePathVideo.toFile().exists()){
            filePathVideo.toFile().mkdirs();
        }
        System.out.println(filePathVideo);
    }
    public static void main(String[] args) {
        test t=new test();
        t.checkFile();
    }
}
