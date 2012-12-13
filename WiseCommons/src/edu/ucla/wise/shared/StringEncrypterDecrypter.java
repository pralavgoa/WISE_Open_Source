package edu.ucla.wise.shared;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Pralav
 * 
 */
public class StringEncrypterDecrypter {

	private static final String UTF8 = "UTF8";
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private final KeySpec keySpec;
	private final SecretKeyFactory secretKeyFactory;
	private final Cipher cipher;
	byte[] arrayBytes;
	private final String myEncryptionScheme;
	private final SecretKey key;


	public StringEncrypterDecrypter(String encryptionKey) throws Exception {
		myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
		arrayBytes = encryptionKey.getBytes(UTF8);
		keySpec = new DESedeKeySpec(arrayBytes);
		secretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
		cipher = Cipher.getInstance(myEncryptionScheme);
		key = secretKeyFactory.generateSecret(keySpec);
	}


	public String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UTF8);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = new String(Base64.encodeBase64(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}


	public String decrypt(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}
}