package edu.ucla.wise.shared;

import java.math.BigInteger;

public class StringEncoderDecoder {

	private StringEncoderDecoder() {
		// This class should not be instantiated.
	}

	public static final boolean checkStringWithKey(String inputString,
			String key) {
		if (encode(inputString).equals(key)) {
			return true;
		} else
			return false;
	}

	public static final String encode(String user_id) {
		int base_numb = Integer.parseInt(user_id) * 31 + 97654;
		String s1 = Integer.toString(base_numb);
		String s2 = Integer.toString(26);
		BigInteger b1 = new BigInteger(s1);
		BigInteger b2 = new BigInteger(s2);

		int counter = 0;
		String char_id = new String();
		while (counter < 5) {
			BigInteger[] bs = b1.divideAndRemainder(b2);
			b1 = bs[0];
			int encode_value = bs[1].intValue() + 65;
			char_id = char_id + (new Character((char) encode_value).toString());
			counter++;
		}
		return char_id;
	}

	public static final String decode(String char_id) {
		String result = new String();
		int sum = 0;
		for (int i = char_id.length() - 1; i >= 0; i--) {
			char c = char_id.charAt(i);
			int remainder = c - 65;
			sum = sum * 26 + remainder;
	}

		sum = sum - 97654;
		int remain = sum % 31;
		if (remain == 0) {
			sum = sum / 31;
			result = Integer.toString(sum);
		} else {
			result = "invalid";
	}
		return result;
	}
}
