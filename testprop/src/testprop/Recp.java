package testprop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Recp {
	static String htmlFile="";

	public static void main(String[] args) throws ParseException {
		//editables
		String[] names = new String[] { "Kishan" };
		String[] rideDates = new String[] { "11/01/2022", "11/02/2022", "11/04/2022", "11/03/2022","11/11/2022","11/14/2022", "11/15/2022","11/04/2022","11/09/2022","11/10/2022","11/09/2022"};
		String[] startTimes = new String[] { "06:31 PM", "09:35 AM", "09:21 AM", "09:23 AM","09:35 AM","09:47 AM", "06:35 PM", "06:21 PM", "06:23 PM","06:35 PM","09:27 AM"};
		String[] endTimes = new String[] { "06:46 PM", "09:46 AM", "09:38 AM", "09:40 AM" ,"09:54 AM","09:58 AM", "06:45 PM", "06:45 PM", "06:46 PM","06:55 PM","09:56 AM"};
		String[] driverNames = new String[] { "Yashpal", "Gurvinder", "Suresh","Navneet","Deepak","Rujesh", "Ravinder", "Sujeet", "Simaranjit","Naman","Davinder"};
		String[] amount = new String[] { "102.25", "127.45", "144.52", "90.57","104.25","106.57", "103.36", "110.40", "109.54","121.20","102.12"};
		
		//dont remove below
		String[] addressRandomness=new String[] {"MMR7+GPJ, Kharar - Landran Rd, Sector 112, Sahibzada Ajit Singh Nagar, Punjab 140307","JLPL, Landran Rd, Sector 94, Sahibzada Ajit Singh Nagar, Punjab 140307, India"};
		String[] licenseRandomness=new String[] {"CH01","PB68","HR02"};
		String[] licenseRandomness_mid=new String[] {"A","B","C"};

		Recp recp = new Recp();
		htmlFile = recp.readFile("D:\\Spring-boot-projects\\testprop\\src\\res\\Receipt_13Sep2022_224032.html");

		for (int i = 0; i < rideDates.length; i++) {
			String receipt=htmlFile;
			receipt=receipt.replace("{{driver_name}}", driverNames[i]);
			receipt=receipt.replace("{{rider_name}}", names[0]);
			receipt=receipt.replace("{{ride_date_MMM_dd_yyyy}}",
					recp.getFormattedDates(rideDates[i], "MM/dd/yyyy", "MMMM dd,yyyy"));
			receipt=receipt.replace("{{ride_date}}", rideDates[i]);
			receipt=receipt.replace("{{time_random_reached}}", recp.getTime(startTimes[i],endTimes[i]));
			receipt=receipt.replace("{{last_four_lic_plate}}", recp.getRandomBetween(2000, 3000));
			String reachedTime = recp.getRandomBetween(12, 20);
			receipt=receipt.replace("{{time_random_reached}}", reachedTime);
			receipt=receipt.replace("{{start_time}}", startTimes[i]);
			receipt=receipt.replace("{{end_time}}", endTimes[i]);
			receipt=receipt.replace("{{address_random}}", addressRandomness[Integer.parseInt(recp.getRandomBetween(0, 2))]);
			receipt=receipt.replace("{{place_license}}", licenseRandomness[Integer.parseInt(recp.getRandomBetween(0, 2))]);
			receipt=receipt.replace("{{kilometers_random}}", recp.getRandomBetween(10, 90));
			receipt=receipt.replace("{{licenseRandomness_mid}}", licenseRandomness_mid[Integer.parseInt(recp.getRandomBetween(0, 3))]);
			
			//amount related stuff

			String[] amountWithRound=recp.amountRoundness(amount[i]);
			String[] amountGst=recp.amountGst(amountWithRound[1]);
			receipt=receipt.replace("{{amount_to_be_paid}}",amountWithRound[1]);
			receipt=receipt.replace("{{before_rounding}}",amount[i]);
			receipt=receipt.replace("{{roundness}}",amountWithRound[0]);
			receipt=receipt.replace("{{without_taxes}}",amountGst[0]);
			receipt=receipt.replace("{{gst}}",amountGst[1]);
			receipt=receipt.replace("{{time_m_e}}", recp.getMorningOrEve(startTimes[i]));
			recp.uploadFile(i+"", names[0], receipt, "D:\\webprac\\recepitr\\pdfs");
		}//

	}
	
	String[] amountRoundness(String amount) {
		Double dblAmt=Double.parseDouble(amount);
		String decAmt=String.format("%.2f",Math.floor(dblAmt)-dblAmt);
		if(decAmt.contains("-")) {
			decAmt=decAmt.replace("-", "₹");
		}
		return new String[] {decAmt,String.valueOf(Math.floor(dblAmt))};
	}
	
	String[] amountGst(String amount) {
		Double doubleAmount=Double.parseDouble(amount);
		Double amt=(doubleAmount*8.8)/100;
		return new String[] {String.valueOf(Math.ceil(doubleAmount)-Math.ceil(amt)),String.valueOf(Math.ceil(amt))};
	}
	
	String getTime(String startTime,String endTime){
		int startT=Integer.parseInt(startTime.split(":")[1].split(" ")[0]);
		int endT=Integer.parseInt(endTime.split(":")[1].split(" ")[0]);
		
		return String.valueOf(endT-startT);
	}
	
	String getMorningOrEve(String amPm) {
		return amPm.contains("AM")?"morning":"evening";
	}

	String readFile(String path) {
		StringBuilder str=new StringBuilder();
		String strs="";
		try(BufferedReader in = new BufferedReader(new FileReader(path))){
			while ((strs=in.readLine()) != null) {
				str.append(strs);
			}
		} catch (IOException e) {
		}
		return str.toString();
	}

	void uploadFile(String fileName, String folderName, String data, String outpath) {
		System.out.println("uploading on "+fileName);
	
		File f = new File(outpath + "\\" + folderName);

		if (!f.exists()) {
			f.mkdirs();
			System.out.println("not exisists");
		}

		Path path = Paths.get(outpath + "\\" + folderName+ "\\"+ fileName + ".html");
		String contents = data;

		try {
			Files.writeString(path, contents, StandardCharsets.UTF_8);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("Some error occured: " + ex.getLocalizedMessage());
		}
	}

	String getFormattedDates(String date, String format, String convertFormat) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date d = dateFormat.parse(date);
		SimpleDateFormat dateFormatConvert = new SimpleDateFormat(convertFormat);
		String dConverted = dateFormatConvert.format(d);
		return dConverted;
	}

	String getRandomBetween(int low, int high) {
		Random r = new Random();
		int result = r.nextInt(high - low) + low;
		return String.valueOf(result);
	}
}
