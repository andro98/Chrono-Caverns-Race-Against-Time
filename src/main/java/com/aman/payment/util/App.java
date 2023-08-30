package com.aman.payment.util;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Year;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class App {

	@Autowired
	static RestTemplate restTemplate;
	

	
//	static String token = 
//			"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiaWF0IjoxNjIxNjk3NTg3LCJleHAiOjE2MjE3MDY1ODd9.1jsCSETgOuoEDbyg2zREsUciWe9cdgtvzlAWeZzWVfpVwJ41UxICI51X3ceM9CfwN3wzh1nMxcix9zeKJcCxsw";
	static String token ="aNj2aqyymtr3LqPERjckfdNxmG8IWEz5iS+U5LsMEWCGzeurHwDVPK3n9dah5NYkHdqT78x2dxOBQAsDo5+rNRixOLs9B1LSYcuq8Fbh49GxIGLGCt4cbgiijlploHBUPxMZ8MEK/9noDCwCdbKyMIIN7od/7/gOtri0TJWWBkgw/GhwecKhwacLQgrWr7z1DPmo8jRlKX/ARdLALv5cSYnRzVbbcRJHQ1B6r5o2xrY=";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String st = "POS001210500032";
//		System.out.println(st.substring(0, 6));
//		System.out.println(st.substring(6, 11));
//		
//		System.out.println(Instant.now().toEpochMilli());
//		
//		DateFormat df = new SimpleDateFormat("yy");
//		String formattedDate = df.format(Calendar.getInstance().getTime());
//		
//		final AtomicLong counter = new AtomicLong();
//		
//		System.out.println(counter.incrementAndGet());

//		int width = 963; //width of the image
//	    int height = 640; //height of the image
//	    // For storing image in RAM
//	    BufferedImage image = null;
//	    // READ IMAGE
//	    try
//	    {
//	      File file = new File("C:\\Users\\Assem Abdelaziz\\Downloads\\1.jpeg");
//	      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//	      // Reading input file
//	      image = ImageIO.read(file);
//	      System.out.println(image.getData());
//	      
////	      Tesseract instance = Tesseract.getInstance();
//	      
//	    }
//	    catch(IOException e)
//	    {
//	      System.out.println("Error: "+e);
//	    }
//		System.out.println(LocalDate.now());
//		System.out.println(Calendar.getInstance().get(Calendar.YEAR));
//		System.out.println(Year.now());
//		System.out.println(LocalDateTime.now().toString().split("\\.")[0].replaceAll(":", "-"));
//		
//		
//		LocalDate today = LocalDate.now();
//		System.out.println("First day: " + today.withDayOfMonth(1));
//		System.out.println("Last day: " + today);
//		System.out.println(java.sql.Date.valueOf(today));
//		
//		Calendar cal = Calendar.getInstance();
//		System.out.println(cal.getTime());
//		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		cal.set(Calendar.MINUTE, 0);
//		cal.set(Calendar.SECOND, 0);
//        System.out.println(cal.getTime());
//        System.out.println(Date.from(Instant.now()));

//		executeLogic();
//		executeLogic2();
		
//		String basePath = "opt/request"+"/"+"20220919";
//		String receiptUrl = "22"
//							+ConfigurationConstant.MAAZOUN_BOOK_REQUEST_REPORT_NAME+".pdf";
//		
//		System.out.println(basePath+receiptUrl);
//		System.out.println(basePath.concat(receiptUrl));
//		
//		String x = "00100000003";
//		System.out.println(x.substring(x.length() - 2));
//		List<String> st = new ArrayList<String>();
//		st.add("1");
//		st.add("2");
//		
//		boolean v = false;
//		String x1 = null;
//		for(String s : st){
//			if(s.equals("1")) {
//				v = true;				
//				break;
//			}
//		}
//		System.out.println(v);
//		
////		String code = "{\"transStatus\":0,\"merchantID\":\"1590981005\",\"terminalID\":\"80661197\",\"responseCode\":\"00\",\"pan\":\"526439******5687\",\"issuerName\":\"MASTER\",\"expiryDate\":null,\"batchNo\":\"1\",\r\n" + 
////				"\"stan\":null,\"amount\":\"000000026639\",\"currency\":null,\"dateTime\":\"20230206175033\",\"authCode\":\"664248\",\"rrn\":\"000004000000\",\"receiptNo\":\"2\",\"inputMethod\":null,\r\n" + 
////				"\"aid\":\"A0000000041010\",\"appName\":\"Debit Mastercard\",\"pinEntryMode\":null,\"cardHolderName\":null}";
////		TransactionECRAudit eTransactionECRAudit = convertJsonStringToObject(code , TransactionECRAudit.class);
//		
//		System.out.println(Year.now().getValue());
//		
//		System.out.println(Date.from(Instant.now()).getTime());
//		System.out.println(Date.from(Instant.now()).getTime());
		
		String sDate = "31803100200999";
//		String sDate = "27306250201081";
		
		String century = sDate.substring(0, 1);
		if(century.equals("2")) {
			century = "19";
		}else if(century.equals("3")) {
			century = "20";
		}
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyyMMdd").parse(century+sDate.substring(1, 7)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(c.getTime());

	}

	private static void executeLogic() {
		for (int i = 0; i < 10; i++) {
			System.out.println(i + " Thread============================================================");

			Thread t1 = new Thread(new Runnable() {
				public void run() {
					try {
						addHoldTransaction();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			t1.start();
//			Thread t2 = new Thread(new Runnable() {
//				public void run() {
//					try {
//						addFinalTransaction();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			});
//			t2.start();

		}
	}
	
	private static void executeLogic2() {
		for (int i = 0; i < 10; i++) {
			System.out.println(i + " Thread============================================================");

			Thread t1 = new Thread(new Runnable() {
				public void run() {
					try {
						addHoldTransaction();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			t1.start();
//			Thread t2 = new Thread(new Runnable() {
//				public void run() {
//					try {
//						addFinalTransaction();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			});
//			t2.start();

		}
	}

	public static void addHoldTransaction() throws Exception {


		for (int i = 0; i < 10; i++) {
//			System.out.println(i + " Business============================================================");
			String url = "http://localhost:9004/api/passport/addHoldTransaction";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// Setting basic post request
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Authorization", "Bearer "+ token);
			con.setRequestProperty("Content-Type", "application/json");

			java.util.Random rng = new java.util.Random();
			long first14 = (rng.nextLong() % 1000000000000L) + 52000000000000L;
			long first11 = (rng.nextLong() % 1000000000L) + 52000000000L;
			long first10 = (rng.nextLong() % 100000000L) + 5200000000L;
			
//			private Set<TransactionItemsRequest> subServiceIds;

			
			String postJsonData = "{\"amount\": 300,"
					+ "	\"idNumber\": "+first14+"," + "	\"applicantMobileNumber\": "+first11+","
					+ "	\"applicantName\": \""+RandomStringUtils.randomAlphabetic(10)+"\","
					+ "	\"locationId\": 2,"
					+ "	\"merchantId\": 1,"
					+ "	\"paymentMethodId\": \"1\","
					+ "	\"posId\": 3," + "	\"serviceId\": 1,"
					+ "	\"createdBy\": 1,"
					+ "	\"tax\": 1,"
					 + "	\"relativePerson\": 1,"
					 + "	\"relativePersonMobile\": 1,"
					 + "	\"relativeType\": 1,"
					 + "	\"passportTypeId\": 1,"
					 + "	\"locationCode\": 1,"
					 + "	\"locationName\": 1,"
					 + "	\"merchantName\": 1,"
					 + "	\"serviceName\": 1,"
					 +" \"subServiceIds\": [],"
					+ "\"visaNumber\": \"\"	}";

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(encrypt(postJsonData));
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
//			System.out.println("nSending 'POST' request to URL : " + url);
//			System.out.println("Post Data : " + postJsonData);
//			System.out.println("Response Code : " + responseCode);
		}

	}
	
	public static String encrypt(String message) {
		String KEY = "9y$B&E)H@McQfTjW";
		String IV = "7w!z%C&F)J@NcRfU";
		if(message != null) {
				byte[] cipherText = new byte[0];
				try {
					cipherText = encrypt(KEY.getBytes("UTF-8"), IV.getBytes("UTF-8"), message.getBytes("UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			return Base64.getEncoder().encodeToString(cipherText);
		}
		return null;
	}
	
	private static byte[] encrypt(final byte[] key, final byte[] IV, final byte[] message) throws Exception {
		return encryptDecrypt(Cipher.ENCRYPT_MODE, key, IV, message);
	}
	
	private static byte[] encryptDecrypt(final int mode, final byte[] key, byte[] iv, final byte[] message)
			throws Exception {
		  String ALGORITHM = "AES";
		  String AES_CBS_PADDING = "AES/CBC/PKCS5Padding";
		final Cipher cipher = Cipher.getInstance(AES_CBS_PADDING);
		final SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
		final IvParameterSpec ivSpec = new IvParameterSpec(iv);
		cipher.init(mode, keySpec, ivSpec);
		return cipher.doFinal(message);
	}
	
	public static void addFinalTransaction() throws Exception {

		for (int i = 0; i < 10; i++) {
//			System.out.println(i + " Business============================================================");
			String url = "http://localhost:9004/api/transaction/addFinalTransaction";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// Setting basic post request
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Authorization", "Bearer "+token);
			con.setRequestProperty("Content-Type", "application/json");

			java.util.Random rng = new java.util.Random();
			long first14 = (rng.nextLong() % 1000000000000L) + 52000000000000L;
			long first11 = (rng.nextLong() % 1000000000L) + 52000000000L;
			long first10 = (rng.nextLong() % 100000000L) + 5200000000L;
			
			String postJsonData = "{\"amount\": 300,\"applicantDOB\": \"2021-04-14\"," + "	\"applicantGender\": 1,"
					+ "	\"applicantIDNumber\": "+first14+"," + "	\"applicantMobileNumber\": "+first11+","
					+ "	\"applicantName\": \""+RandomStringUtils.randomAlphabetic(10)+"\"," + "	\"applicantNameInPassport\": \"mohammad\","
					+ "	\"applicantPOB\": \"Damietta\"," + "	\"createdBy\": \"admin\"," + "	\"locationId\": 1,"
					+ "	\"merchantId\": 1," + "	\"passportExpiryDate\": \"2021-01-14\","
					+ "	\"passportIssueDate\": \"2021-04-14\"," + "	\"passportIssueOffice\": \"alex\","
					+ "	\"passportNumber\": "+first10+"," + "	\"paymentMethodId\": \"1\","
					+ "	\"posId\": 1," + "	\"serviceId\": 1," + "	\"status\": \"Completed\","
					+ "	\"visaNumber\": \"\"" + "	}";

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postJsonData);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
//			System.out.println("nSending 'POST' request to URL : " + url);
//			System.out.println("Post Data : " + postJsonData);
//			System.out.println("Response Code : " + responseCode);
		}

	}

}
