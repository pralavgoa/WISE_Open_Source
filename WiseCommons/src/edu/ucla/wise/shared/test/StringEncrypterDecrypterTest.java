package edu.ucla.wise.shared.test;

import edu.ucla.wise.shared.StringEncrypterDecrypter;

public class StringEncrypterDecrypterTest {
	public static void main(String args[]) throws Exception {
		StringEncrypterDecrypter td = new StringEncrypterDecrypter(
				"pralavpralavpralavpralav");

		String target = "This:is@big1234";
		String encrypted = td.encrypt(target);
		String decrypted = td.decrypt(encrypted);

		System.out.println("String To Encrypt: " + target);
		System.out.println("Encrypted String:" + encrypted);
		System.out.println("Decrypted String:" + decrypted);

	}
}
