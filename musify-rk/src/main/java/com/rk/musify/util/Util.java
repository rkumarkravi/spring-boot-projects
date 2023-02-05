package com.rk.musify.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
		log.info("%x\n", myRandomNumber);
		String hex = Integer.toHexString(myRandomNumber);
		log.info("randomhex is {}", myRandomNumber);
		return hex;
	}

	public static String getExtension(String fileName) {
		if (fileName != null && fileName.contains(".")) {
			return fileName.substring(fileName.length() - 4, fileName.length()).trim();
		} else {
			return ".mp3";
		}
	}

	public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

		ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0xFFFFC041);

		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
		byte[] pngData = pngOutputStream.toByteArray();
		return pngData;
	}

	public static String getSecondsFromMusicFile(InputStream file) throws UnsupportedAudioFileException, IOException {

		try {
//			File file = new File("C:\\Users\\errku\\Documents\\songs\\Humnavamere.mp3");
			Parser parser = new Mp3Parser();
			ContentHandler handler = new DefaultHandler();
			Metadata metadata = new Metadata();
			ParseContext parseContext = new ParseContext();
			parser.parse(file, handler, metadata, parseContext);
			String duration = metadata.get("xmpDM:duration");
			System.out.println("Duration in miliseconds: " + duration);
			return duration;
		} catch (IOException | SAXException | TikaException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getRGBFromImage(InputStream imageFileStream) throws IOException {
		BufferedImage image = ImageIO.read(imageFileStream);

		int width = image.getWidth();
		int height = image.getHeight();

		int r = 0;
		int g = 0;
		int b = 0;
		int total = 0;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Color color = new Color(image.getRGB(x, y));
				r += color.getRed();
				g += color.getGreen();
				b += color.getBlue();
				total++;
			}
		}

		r = r / total;
		g = g / total;
		b = b / total;

		Color dominantColor = new Color(r, g, b);
		String rgb = "rgb(" + dominantColor.getRed() + "," + dominantColor.getGreen() + "," + dominantColor.getBlue()
				+ ")";
		return rgb;

	}
}
