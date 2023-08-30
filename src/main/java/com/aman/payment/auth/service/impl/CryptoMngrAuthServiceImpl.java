package com.aman.payment.auth.service.impl;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aman.payment.auth.service.CryptoMngrAuthService;

/**
 * @author Aya Esmail
 */

@Service
public class CryptoMngrAuthServiceImpl implements CryptoMngrAuthService {
	public static String ALGORITHM = "AES";
	private static String AES_CBS_PADDING = "AES/CBC/PKCS5Padding";

	@Value("${encryption.key}")
	private String KEY;
	@Value("${encryption.iv}")
	private String IV;

	private byte[] encrypt(final byte[] key, final byte[] IV, final byte[] message) throws Exception {
		return CryptoMngrAuthServiceImpl.encryptDecrypt(Cipher.ENCRYPT_MODE, key, IV, message);
	}

	public String encrypt(String message) {
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
		return CryptoMngrAuthServiceImpl.encryptDecrypt(Cipher.DECRYPT_MODE, key, iv, message);
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
