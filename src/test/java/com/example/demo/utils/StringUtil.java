package com.example.demo.utils;

import java.util.Random;

public class StringUtil {

	public static String generateRandomChars() {
		int length = new Random().nextInt(10);

		String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}

		return sb.toString();
	}

}
