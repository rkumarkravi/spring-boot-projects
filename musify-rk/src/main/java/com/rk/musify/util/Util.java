package com.rk.musify.util;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {

	public static String generateRandomHex() {
		Random rand = new Random();
		int myRandomNumber = rand.nextInt(0x1000000) + 0x10000000;
		log.info("%x\n",myRandomNumber);
		String hex = Integer.toHexString(myRandomNumber);
		log.info("randomhex is {}",myRandomNumber);
		return hex;
	}
	
	public static String getExtension(String fileName) {
		if(fileName!=null && fileName.contains(".")) {
			return "."+fileName.split(".")[1];
		}else {
			return "."+"mp3";
		}
	}
	
	
}
