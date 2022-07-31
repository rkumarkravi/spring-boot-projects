package com.rk.musify.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

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
	
	public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig( 0xFF000002 , 0xFFFFC041 ) ;

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }
	
}
