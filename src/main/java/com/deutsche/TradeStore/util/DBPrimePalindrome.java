package com.deutsche.TradeStore.util;

public class DBPrimePalindrome {

	public static void main(String args[]) {

		int num = 797;

		if (isPrime(num) && isPalindrome(num))
			System.out.println("Prime Palindrome");
		else
			System.out.println("Not a Prime Palindrome");
	}

	private static boolean isPrime(int num) {
		boolean isPrm = true;

		for (int i = 2; i < num; i++) {
			if (num % i == 0) {
				isPrm = false;
				break;
			}
		}

		return isPrm;
	}

	private static boolean isPalindrome(int num) {
		boolean isPlndrm = false;

		int remainder, sum = 0, reversed;
		
		reversed = num;
		while (num > 0) {
			remainder = num % 10; 
			sum = (sum * 10) + remainder;
			num = num / 10;
		}
		if (reversed == sum)
			isPlndrm = true;
			
		return isPlndrm;

	}
}
