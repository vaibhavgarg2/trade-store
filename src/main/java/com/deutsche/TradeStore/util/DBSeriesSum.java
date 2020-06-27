package com.deutsche.TradeStore.util;

public class DBSeriesSum {

	public static void main(String args[]) {
		//x^2/1!+x^4/3! + x^6/5! + ..... + x^2n/(2n-1)!
		//Assumption = x & n are positive
		double sum = findSeriesSum(3, 2);
		System.out.println("Sum of series is : " + sum);
	}
	
	private static double findSeriesSum(int x, int n) {
		double result = 0;
		
		for(int i = 1; i <= n ; i++) {
			double power = findPower(x, 2*i);
			double factorial = findFactorial(2*i-1);
			System.out.println("Power : " + power);
			System.out.println("Factorial of "+ i + " is : " + factorial);
			result += power/factorial;
		}
		
		return result;
	}
	
	private static double findPower(int x, int n) {
		if(n == 0) {
			return 1;
		} else
			return x * findPower(x, n-1);
		
	}
	
	private static double findFactorial(int n) {
		
		double factorial = 1; 
		
		for(int i = 1 ; i <= n ; i++) {
			factorial = factorial * i;
		}
		
		return factorial;
	}
	
}
