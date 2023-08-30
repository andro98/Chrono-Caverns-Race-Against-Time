package com.aman.payment.util;


import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.time.format.FormatStyle;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Test{


	public static String ALGORITHM = "AES";
	private static String AES_CBS_PADDING = "AES/CBC/PKCS5Padding";

	private static String KEY = "JO0KvgQ8S2uKXmvn";
	private static String IV = "ojPLvtz3mb2IXmvn";
	
	public static void main(String[] args) throws Exception {

//		System.out.println(encrypt("1590045000000027"));
		
Date createdAt = Date.from(Instant.now());
HijrahDate hijrahDate=HijrahDate.now();
		
//		String refRequestNumberTimestamp = new SimpleDateFormat("sS").format(createdAt);
System.out.println(hijrahDate);

Locale arabicLocale = Locale.forLanguageTag("ar");
DateTimeFormatter arabicDateFormatter
        = DateTimeFormatter.ofPattern("uuuu/MM/dd", arabicLocale);
LocalDate today = LocalDate.now(ZoneId.of("Asia/Muscat"));
System.out.println(today.format(arabicDateFormatter));

//		System.out.println(new SimpleDateFormat("dd/MM/yyyy hh.mm aa").format(createdAt));

	}

	private static byte[] encrypt(final byte[] key, final byte[] IV, final byte[] message) throws Exception {
		return Test.encryptDecrypt(Cipher.ENCRYPT_MODE, key, IV, message);
	}

	public static String encrypt(String message) {
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

	public String decrypt(String message) {
		if(message != null) {
			byte[] cipherText = new byte[0];
			String result = null;
			try {
				cipherText = decrypt(KEY.getBytes("UTF-8"), IV.getBytes("UTF-8"), Base64.getDecoder().decode(message.trim()));
				result = new String(cipherText, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		return null;
	}

	private byte[] decrypt(byte[] key, byte[] iv, byte[] message) throws Exception {
		return Test.encryptDecrypt(Cipher.DECRYPT_MODE, key, iv, message);
	}

	private static byte[] encryptDecrypt(final int mode, final byte[] key, byte[] iv, final byte[] message)
			throws Exception {
		final Cipher cipher = Cipher.getInstance(AES_CBS_PADDING);
		final SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
		final IvParameterSpec ivSpec = new IvParameterSpec(iv);
		cipher.init(mode, keySpec, ivSpec);
		return cipher.doFinal(message);
	}


}
