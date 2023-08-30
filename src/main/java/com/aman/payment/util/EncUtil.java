package com.aman.payment.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


public class EncUtil {
	  
	  public static String keyOfkey = "keyEncryptionKey";
	  private static final String initVector = "encryptionIntVec";
	  
	  private static String decryptKey(String encrypted) {
		    try {
		        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
		        SecretKeySpec skeySpec = new SecretKeySpec(keyOfkey.getBytes("UTF-8"), "AES");
		 
		        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		        byte[] original = cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted));
		 
		        return new String(original);
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		 
		    return null;
		}
	  
	  public static String decrypt(String encrypted) {
		    try {
		        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
		        SecretKeySpec skeySpec = new SecretKeySpec(decryptKey("HoYCPA8MNrgyw17oN5pSo47jb2xPyZ2g9Hs5b4KBB/U=").getBytes("UTF-8"), "AES");
		 
		        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		        byte[] original = cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted));
		 
		        return new String(original, "UTF-8");
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		 
		    return null;
		}
	   
	  public static void main(String[] args) {
			
			System.out.println(decrypt("DsfKpHn9Rd3Gcm2ZLW7phMNPUIVijCeQCI23Q28OtWfdnhOJ67F9mzB2dyO47MBUJqGYtZanAIRfw0JP3r2PS3kwktDwQbILwvx/TZGnzOCt3FzpRUyww3Q22n6HP/zao1faN5kR8Cj0L/Nuzsd36455jKJXlXizeHPGQ7DDCJDOnzjk90HbPXWNnZj6mxdCPOWOG93oVxJ9EnDA+InVemO9lfo9KVctTKrqKvu1xSEyOJ0Uxsv0ipOb9Fp5fgmr"));
		
			
		}
}
