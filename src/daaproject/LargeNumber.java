package daaproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class LargeNumber {

	public static void generateTimings() throws IOException, FileNotFoundException {

		int power = 12; // this will generate a number with 2^power digits
		// if you set it to 20 it will generate a number with 1,048,576 digits

		File file = new File("large_number_results.csv");
		FileWriter fr = null;
		StringBuilder sb = new StringBuilder();

		try {
			fr = new FileWriter(file);
			sb.append("Large Number Multiplication");
			sb.append("\n");
			sb.append("Number of digits");
			sb.append(',');
			sb.append("Gauss algorithm - Time in milliseconds");
			sb.append(',');
			sb.append("Karatsuba algorithm - Time in milliseconds");
			sb.append('\n');

			for (int i = 0; i <= power; i++) {
				sb.append(i + "," + runGauss(i, i) + "," + runKaratsuba(i, i) + "\n");
			}

			fr.write(sb.toString());
			sb.setLength(0);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close resources
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static long runKaratsuba(long digitsInFirstNumber, long digitsInSecondNumber)
			throws IOException, FileNotFoundException {
		String d1 = "";
		for (double a1 = 0; a1 < Math.pow(2, (double) digitsInFirstNumber); a1++) {
			Random rand = new Random();
			int b1 = rand.nextInt(10);
			while (a1 == 0 && b1 == 0) {
				b1 = rand.nextInt(10);
			}
			String c1 = String.valueOf(b1);
			d1 = d1 + c1;
		}

		String d2 = "";
		for (double a2 = 0; a2 < Math.pow(2, (double) digitsInSecondNumber); a2++) {
			Random rand = new Random();
			int b2 = rand.nextInt(10);
			while (a2 == 0 && b2 == 0) {
				b2 = rand.nextInt(10);
			}
			String c2 = String.valueOf(b2);
			d2 = d2 + c2;
		}
		// Print the 2 numbers//
//		System.out.println("first: " + d1);
//		System.out.println("second: " + d2);
//		System.out.println("");

//        FileWriter fileW = new FileWriter("C:\\Users\\sneha\\Documents\\Project_IO_Files\\input.txt");
//        fileW.write(d1);
//        fileW.write(System.lineSeparator());
//        fileW.write(System.lineSeparator());
//        fileW.write(d2);
//        fileW.close();
//        
//        File fileR= new File("C:\\Users\\sneha\\Documents\\Project_IO_Files\\input.txt");
//        Scanner sc=new Scanner(fileR);
//        s1=sc.nextLine();
//        sc.nextLine();
//        s2=sc.nextLine();
//        sc.close();

		long startTime = System.currentTimeMillis();
		// epoch
		String prod = karatsuba(d1, d2);
		String Product = trimZero(prod);
//		System.out.println("Product: " + Product);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
//		System.out.println("Total Time taken by Karatsuba is :" + totalTime + "milliseconds");

		return totalTime;
//        fileW = new FileWriter("C:\\Users\\sneha\\Documents\\Project_IO_Files\\output.txt"); 
//        fileW.write(prod);
//        fileW.write(System.lineSeparator());
//        fileW.write(System.lineSeparator());
//        fileW.write(String.valueOf(totalTime));
//        fileW.close();
//
//		// to validate the computed result
//		String bigMul = new BigInteger(d1).multiply(new BigInteger(d2)).toString();
//		System.out.println(bigMul.equals(Product));
//		System.out.println("bigProd: " + bigMul);
	}

	public static long runGauss(long digitsInFirstNumber, long digitsInSecondNumber)
			throws IOException, FileNotFoundException {
		String d1 = "";
		for (double a1 = 0; a1 < Math.pow(2, (double) digitsInFirstNumber); a1++) {
			Random rand = new Random();
			int b1 = rand.nextInt(10);
			while (a1 == 0 && b1 == 0) {
				b1 = rand.nextInt(10);
			}
			String c1 = String.valueOf(b1);
			d1 = d1 + c1;
		}

		String d2 = "";
		for (double a2 = 0; a2 < Math.pow(2, (double) digitsInSecondNumber); a2++) {
			Random rand = new Random();
			int b2 = rand.nextInt(10);
			while (a2 == 0 && b2 == 0) {
				b2 = rand.nextInt(10);
			}
			String c2 = String.valueOf(b2);
			d2 = d2 + c2;
		}
		// Print the 2 numbers//
//		System.out.println("first: " + d1);
//		System.out.println("second: " + d2);
//		System.out.println("");

		long startTime = System.currentTimeMillis();
		// epoch
		String prod = gauss(d1, d2);
		String Product = trimZero(prod);
//		System.out.println("Product: " + Product);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
//		System.out.println("Total Time taken by gauss is :" + totalTime + "milliseconds");
		return totalTime;
//
//		// to validate the computed result
//		String bigMul = new BigInteger(d1).multiply(new BigInteger(d2)).toString();
//		System.out.println(bigMul.equals(Product));
//		System.out.println("bigProd: " + bigMul);
	}

	public static long runLongMultiplication(long digitsInFirstNumber, long digitsInSecondNumber)
			throws IOException, FileNotFoundException {
		String d1 = "";
		for (double a1 = 0; a1 < Math.pow(2, (double) digitsInFirstNumber); a1++) {
			Random rand = new Random();
			int b1 = rand.nextInt(10);
			while (a1 == 0 && b1 == 0) {
				b1 = rand.nextInt(10);
			}
			String c1 = String.valueOf(b1);
			d1 = d1 + c1;
		}

		String d2 = "";
		for (double a2 = 0; a2 < Math.pow(2, (double) digitsInSecondNumber); a2++) {
			Random rand = new Random();
			int b2 = rand.nextInt(10);
			while (a2 == 0 && b2 == 0) {
				b2 = rand.nextInt(10);
			}
			String c2 = String.valueOf(b2);
			d2 = d2 + c2;
		}
		// Print the 2 numbers//
		System.out.println("first: " + d1);
		System.out.println("second: " + d2);
		System.out.println("");

		long startTime = System.currentTimeMillis();
		// epoch
		String prod = longMultiplication(d1, d2);
		String Product = trimZero(prod);
		System.out.println("Product: " + Product);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total Time taken by longMultiplication is :" + totalTime + "milliseconds");
		return totalTime;
//
		// to validate the computed result
		String bigMul = new BigInteger(d1).multiply(new BigInteger(d2)).toString();
		System.out.println(bigMul.equals(Product));
		System.out.println("bigProd: " + bigMul);
	}

	private static byte[] stringToDigits(String num) {
		byte[] result = new byte[num.length()];
		for (int i = 0; i < num.length(); i++) {
			char c = num.charAt(i);
			if (c < '0' || c > '9') {
				throw new IllegalArgumentException("Invalid digit " + c + " found at position " + i);
			}
			result[num.length() - 1 - i] = (byte) (c - '0');
		}
		return result;
	}

	private static String longMultiplication(String d1, String d2) {
		// TODO Auto-generated method stub
		byte[] left = stringToDigits(d1);
		byte[] right = stringToDigits(d2);
		byte[] result = new byte[left.length + right.length];
		for (int rightPos = 0; rightPos < right.length; rightPos++) {
			byte rightDigit = right[rightPos];
			byte temp = 0;
			for (int leftPos = 0; leftPos < left.length; leftPos++) {
				temp += result[leftPos + rightPos];
				temp += rightDigit * left[leftPos];
				result[leftPos + rightPos] = (byte) (temp % 10);
				temp /= 10;
			}
			int destPos = rightPos + left.length;
			while (temp != 0) {
				temp += result[destPos] & 0xFFFFFFFFL;
				result[destPos] = (byte) (temp % 10);
				temp /= 10;
				destPos++;
			}
		}
		StringBuilder stringResultBuilder = new StringBuilder(result.length);
		for (int i = result.length - 1; i >= 0; i--) {
			byte digit = result[i];
			if (digit != 0 || stringResultBuilder.length() > 0) {
				stringResultBuilder.append((char) (digit + '0'));
			}
		}
		return stringResultBuilder.toString();
	}

	public static String gauss(String x, String y) {
		// for small num, direct multiply
		if (x.length() <= 2 && y.length() <= 2) {
			return String.valueOf(Integer.valueOf(x) * Integer.valueOf(y));
		}

		int n = Math.max(x.length(), y.length());
		// find middle point of the digits//
		int m = n / 2;
		int m1 = x.length() - m;
		int m2 = y.length() - m;
		if (m1 < 0) {
			m1 = 0;
		}
		if (m2 < 0) {
			m2 = 0;
		}
		String a = x.substring(0, m1);
		if (a.isEmpty()) {
			a = "0";
		}
		String b = x.substring(m1);
		String c = y.substring(0, m2);
		if (c.isEmpty()) {
			c = "0";
		}
		String d = y.substring(m2);

		String ac = gauss(a, c);
		ac = appendZero(ac, n);
		String ad = gauss(a, d);
		String bc = gauss(b, c);
		String X = sumString(ad, bc);
		X = appendZero(X, n / 2);
		String bd = gauss(b, d);
		String result = sumString(sumString(ac, X), bd);
		return result;
	}

	public static String karatsuba(String x, String y) {
		// for small num, direct multiply
		if (x.length() <= 2 && y.length() <= 2) {
			return String.valueOf(Integer.valueOf(x) * Integer.valueOf(y));
		}

		int n = Math.max(x.length(), y.length());
		// find middle point of the digits//
		int m = n / 2;
		int m1 = x.length() - m;
		int m2 = y.length() - m;
		if (m1 < 0) {
			m1 = 0;
		}
		if (m2 < 0) {
			m2 = 0;
		}
		String a = x.substring(0, m1);
		if (a.isEmpty()) {
			a = "0";
		}
		String b = x.substring(m1);
		String c = y.substring(0, m2);
		if (c.isEmpty()) {
			c = "0";
		}
		String d = y.substring(m2);
		String S1 = karatsuba(a, c);
		String S3 = karatsuba(sumString(a, b), sumString(c, d));
		String S2 = karatsuba(b, d);
		String S4 = diffString(diffString(S3, S2), S1);
		String S5 = sumString(sumString(appendZero(S1, m * 2), S2), appendZero(S4, m));
		// 2m is x.length//
		return S5;

	}

	// find max of 2 strings//

	public static String maxString(String x, String y) {
		if (x.length() > y.length()) {
			return x;
		} else if (x.length() < y.length()) {
			return y;
		} else {
			for (int i = 0; i < x.length(); i++) {
				char x1 = x.charAt(i);
				char y1 = y.charAt(i);
				if (x1 > y1) {
					return x;
				} else if (x1 < y1) {
					return y;
				}
			}
			return x;
		}
	}

//sum of 2 strings

	public static String sumString(String x, String y) {

		// x +(-y) = x-y
		// -x +(+y) = (y-x)
		// -x + (-y) = -(x+y)
		if (y.charAt(0) == '-' && x.charAt(0) != '-') {
			return diffString(x, y.substring(1));
		} else if (x.charAt(0) == '-' && y.charAt(0) != '-') {
			return diffString(y, x.substring(1));
		} else if (x.charAt(0) == '-' && y.charAt(0) == '-') {
			String d = sumString(x.substring(1), y.substring(1));
			return '-' + d;
		}

		if (x.length() > y.length()) {
			/*
			 * prepend the diff of digits with 0 for y now add x n y
			 */
			String a = "";
			for (int i = 0; i < x.length() - y.length(); i++) {
				a = a + 0;
			}
			y = a + y;
		} else if (x.length() < y.length()) {
			/*
			 * prepend the diff of digits with 0 for x now add x n y
			 */
			String a = "";
			for (int i = 0; i < y.length() - x.length(); i++) {
				a = a + 0;
			}
			x = a + x;
		}
		int carry = 0;
		String sum = "";
		char[] x2 = x.toCharArray();
		char[] y2 = y.toCharArray();
		for (int i = x.length() - 1; i >= 0; i--) {
			int x1 = x2[i] - '0';
			int y1 = y2[i] - '0';
			x1 = x1 + carry;
			carry = 0;
			int z1 = x1 + y1;
			if (z1 >= 10) {
				carry = 1;
				z1 = z1 - 10;
				sum = sum + z1;
			} else {
				sum = sum + z1;
			}
		}

		if (carry != 0) {
			sum = sum + "1";
		}

		StringBuilder s = new StringBuilder();
		s.append(sum);
		s = s.reverse();
		return s.toString();
	}

	// difference of 2 strings

	public static String diffString(String x, String y) {
		// x -(-y) = x+y
		// -x -(+y) = -(x+y)
		// -x - (-y) = -x+y
		if (y.charAt(0) == '-' && x.charAt(0) != '-') {
			return sumString(x, y.substring(1));
		} else if (x.charAt(0) == '-' && y.charAt(0) != '-') {
			String d = sumString(x.substring(1), y);
			return '-' + d;
		} else if (x.charAt(0) == '-' && y.charAt(0) == '-') {
			String d = maxString(x.substring(1), y.substring(1));
			if (d == x.substring(1)) {
				String e = diffString(x.substring(1), y.substring(1));
				return '-' + e;
			} else {
				String e = diffString(y.substring(1), x.substring(1));
				return e;
			}
		}

		String z = maxString(x, y);
		String diff = "";
		boolean isNegative = false;
		if (z == y) {
			isNegative = true;
			String tmp = x;
			x = y;
			y = tmp;
		}

		if (x.length() > y.length()) {
			/*
			 * prepend the diff of digits with 0 for y now add x n y
			 */
			String a = "";
			for (int i = 0; i < x.length() - y.length(); i++) {
				a = a + 0;
			}
			y = a + y;
		} else if (x.length() < y.length()) {
			/*
			 * prepend the diff of digits with 0 for x now add x n y
			 */
			String a = "";
			for (int i = 0; i < y.length() - x.length(); i++) {
				a = a + 0;
			}
			x = a + x;
		}

		int borrow = 0;
		char[] x2 = x.toCharArray();
		char[] y2 = y.toCharArray();
		for (int i = x.length() - 1; i >= 0; i--) {
			int x1 = x2[i] - '0';
			x1 = x1 + borrow;
			int y1 = y2[i] - '0';
			borrow = 0;
			if (x1 >= y1) {
				diff = diff + (x1 - y1);
			} else if (x1 < y1) {
				borrow--;
				x1 = 10 + x1;
				diff = diff + (x1 - y1);
			}
		}
		StringBuilder s = new StringBuilder();
		s.append(diff);
		s = s.reverse();
		String s1 = trimZero(s.toString());

		if (isNegative) {
			return "-" + s1;
		} else {
			return s1;
		}
	}

	// trim 0's in the start of a string in diff
	public static String trimZero(String x) {
		for (int i = 0; i < x.length(); i++) {
			if (x.charAt(i) != '0') {
				String x1 = x.substring(i);
				return x1;
			}
		}
		return "0";
	}

	// karatsuba S5 function_append 0's
	public static String appendZero(String x, int length) {
		// S1 * (long) Math.pow(10, m * 2)//
		for (int i = 0; i < length; i++) {
			x = x + 0;
		}
		return x;
	}
}
