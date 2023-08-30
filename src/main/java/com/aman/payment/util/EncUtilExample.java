package com.aman.payment.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class EncUtilExample {
	  
	  public static String KEY = "ToXJwoat0F7YsSV0";
	  private static final String IV = "ToXJwoat0F7YsSV0";
	  
	  public static String ALGORITHM = "AES";
		private static String AES_CBS_PADDING = "AES/CBC/PKCS5Padding";
	  
	  private static byte[] encrypt(final byte[] key, final byte[] IV, final byte[] message) throws Exception {
			return encryptDecrypt(Cipher.ENCRYPT_MODE, key, IV, message);
		}

		public static String encryptExternal(String message) {
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

		public static String decryptExternal(String message) {
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

		private static byte[] decrypt(byte[] key, byte[] iv, byte[] message) throws Exception {
			return encryptDecrypt(Cipher.DECRYPT_MODE, key, iv, message);
		}

		private static byte[] encryptDecrypt(final int mode, final byte[] key, byte[] iv, final byte[] message)
				throws Exception {
			final Cipher cipher = Cipher.getInstance(AES_CBS_PADDING);
			final SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
			final IvParameterSpec ivSpec = new IvParameterSpec(iv);
			cipher.init(mode, keySpec, ivSpec);
			return cipher.doFinal(message);
		}
	   
	  public static void main(String[] args) {
			
//		  System.out.println(encryptExternal("{ "+
//					"\"username\":\"wpo.moi\",\r\n" + 
//			  		"\"password\":\"Aman@123\"}"));

//		  System.out.println(encryptExternal("{ "+
//				  "\"username\":\"wpo.moi\",\r\n" + 
//			  	"\"password\":\"Aman@123\",\r\n" +
//		  		"\"nationalId\":\"28112121224566\"}"));
		  
		  System.out.println(encryptExternal("{\r\n" + 
			  		"\"username\":\"wpo.moi\",\r\n" + 
			  		"\"password\":\"AmanXjoUpWm@8564\",\r\n" + 
			  		"\"nationalId\":\"391121212240022\",\r\n" + 
			  		"\"applicantFirstName\":\"test\",\r\n" + 
			  		"\"applicantSecondName\":\"test\",\r\n" + 
			  		"\"applicantThirdName\":\"test\",\r\n" + 
			  		"\"applicantLastName\":\"test\",\r\n" + 
			  		"\"applicantMobile\":\"01277970639\",\r\n" + 
			  		"\"applicantAddress\":\"test\",\r\n" + 
			  		"\"relativePerson\":\"test\",\r\n" + 
			  		"\"relativePersonMobile\":\"01277970639\",\r\n" + 
			  		"\"passportNumber\":\"A1238889\",\r\n" + 
			  		"\"passportIssueDate\":\"2021-01-26\",\r\n" + 
			  		"\"passportExpiryDate\":\"2028-01-25\",\r\n" + 
			  		"\"payCode\":\"1310test\",\r\n" + 
			  		"\"travelDestinationId\":10,\r\n" +  
			  		"\"insuranceDurationId\":3}"));
		  
			System.out.println(decryptExternal("44LMKKrUWorEI7R+7JBskl2HUZo7S/aB54nKE6M+0LFcp+bsdS3YdZ6NPMzdm7qMKjlwVNmb4m58pGcMMihi5+wKifWfuho71GLNr0XpRjyoTLHkgcfRktStgOviedXg5c7SLB3gzn3YtdiT64nrFgDIFKBs7/BxxpWmVOlQjPFSuYwtC8j9Lt4vpuAfGgSSqIhKoZ+cv6LttRGTlFGyMwgyZx3k6nWG/DdMhZJKFcFZ95zrNRrM5MLZSBVxA3MzCY696CCU/7h8y6Vj2BgcD5ylLl9/b2OVeZMKbbTNxhU="));
		
			
		}
}
