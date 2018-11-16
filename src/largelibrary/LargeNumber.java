package largelibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
			sb.append("Traditional Long Multiplication algorithm - Time in milliseconds");
			sb.append(',');
			sb.append("Gauss algorithm - Time in milliseconds");
			sb.append(',');
			sb.append("Karatsuba algorithm - Time in milliseconds");
			sb.append('\n');

			for (int i = 0; i <= power; i++) {
				sb.append((int) Math.pow(2, i) + "," + runLongMultiplication(i, i) + ", " + runGauss(i, i) + ","
						+ runKaratsuba(i, i) + "\n");
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
//
		return totalTime;
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
//
//		// to validate the computed result
//		String bigMul = new BigInteger(d1).multiply(new BigInteger(d2)).toString();
//		System.out.println(bigMul.equals(Product));
//		System.out.println("bigProd: " + bigMul);
		return totalTime;
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
//		System.out.println("first: " + d1);
//		System.out.println("second: " + d2);
//		System.out.println("");

		long startTime = System.currentTimeMillis();
		// epoch
		String prod = longMultiplication(d1, d2);
		String Product = trimZero(prod);
//		System.out.println("Product: " + Product);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;

//		System.out.println("Total Time taken by longMultiplication is :" + totalTime + "milliseconds");
//		// to validate the computed result
//		String bigMul = new BigInteger(d1).multiply(new BigInteger(d2)).toString();
//		System.out.println(bigMul.equals(Product));
//		System.out.println("bigProd: " + bigMul);
//
		return totalTime;

	}

	public static byte[] stringTobytes(String n1) {
		byte[] output = new byte[n1.length()];
		for (int i = 0; i < n1.length(); i++) {
			char c = n1.charAt(i);
			if (c < '0' || c > '9') {
				throw new RuntimeException("digit other than 0-9 seen - aborting...");
			}
			output[n1.length() - 1 - i] = (byte) (c - '0');
		}
		return output;
	}

	public static String longMultiplication(String x, String y) {
//		System.out.println("longMultiplication " + x + " \t x \t " + y);
		if (y.charAt(0) == '-' && x.charAt(0) != '-') {
			return '-' + longMultiplicationUtil(x, y.substring(1));
		} else if (x.charAt(0) == '-' && y.charAt(0) != '-') {
			return '-' + longMultiplicationUtil(x.substring(1), y);
		} else if (x.charAt(0) == '-' && y.charAt(0) == '-') {
			return longMultiplicationUtil(x.substring(1), y.substring(1));
		} else {
			return longMultiplicationUtil(x, y);
		}
	}

	private static String longMultiplicationUtil(String d1, String d2) {
		byte[] finalleft = stringTobytes(d1);
//		printByte(finalleft);
		byte[] finalright = stringTobytes(d2);
//		printByte(finalright);
		byte[] output = new byte[finalleft.length + finalright.length];
//		printByte(output);
		for (int fri = 0; fri < finalright.length; fri++) { // fri - final right index
			byte rightDigit = finalright[fri];
			byte temp = 0;
			for (int fli = 0; fli < finalleft.length; fli++) { // fli - final left index
				temp += output[fli + fri];
				temp += rightDigit * finalleft[fli];
				output[fli + fri] = (byte) (temp % 10);
				temp = (byte) (temp / 10);
			}
			int destIndex = fri + finalleft.length;
			while (temp != 0) {
				output[destIndex] = (byte) (temp % 10);
				temp = (byte) (temp / 10);
				destIndex++;
			}
		}

		StringBuilder stringResultBuilder = new StringBuilder(output.length);
		for (int i = output.length - 1; i >= 0; i--) {
			byte digit = output[i];
			if (digit != 0 || stringResultBuilder.length() > 0) {
				stringResultBuilder.append((char) (digit + '0'));
			}
		}
		if (stringResultBuilder.length() == 0) {
			return "0";
		}
		return stringResultBuilder.toString();
	}

	private static void printByte(byte[] result) {
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i] + " ");
		}
		System.out.println("");
	}

	public static String gauss(String x, String y) {
//		System.out.println("gauss " + x + " \t x \t " + y);
		if (y.charAt(0) == '-' && x.charAt(0) != '-') {
			return '-' + gaussUtil(x, y.substring(1));
		} else if (x.charAt(0) == '-' && y.charAt(0) != '-') {
			return '-' + gaussUtil(x.substring(1), y);
		} else if (x.charAt(0) == '-' && y.charAt(0) == '-') {
			return gaussUtil(x.substring(1), y.substring(1));
		} else {
			return gaussUtil(x, y);
		}
	}

	private static String gaussUtil(String x, String y) {
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
//		System.out.println("karatsuba " + x + " \t x \t " + y);
		if (y.charAt(0) == '-' && x.charAt(0) != '-') {
			return '-' + karatsubaUtil(x, y.substring(1));
		} else if (x.charAt(0) == '-' && y.charAt(0) != '-') {
			return '-' + karatsubaUtil(x.substring(1), y);
		} else if (x.charAt(0) == '-' && y.charAt(0) == '-') {
			return karatsubaUtil(x.substring(1), y.substring(1));
		} else {
			return karatsubaUtil(x, y);
		}
	}

	private static String karatsubaUtil(String x, String y) {
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
//		System.out.println("sumString \t x:" + x + " \t y:" + y);
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
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < x.length() - y.length(); i++) {
				sb.append('0');
			}
			sb.append(y);
			y = sb.toString();
		} else if (x.length() < y.length()) {
			/*
			 * prepend the diff of digits with 0 for x now add x n y
			 */
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < y.length() - x.length(); i++) {
				sb.append('0');
			}
			sb.append(x);
			x = sb.toString();
		}
		int carry = 0;
		StringBuilder sb = new StringBuilder();
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
				sb.append(z1);
			} else {
				sb.append(z1);
			}
		}

		if (carry != 0) {
			sb.append("1");
		}

		sb = sb.reverse();
		return sb.toString();
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
		StringBuilder diff = new StringBuilder();
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
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < x.length() - y.length(); i++) {
				sb.append('0');
			}
			sb.append(y);
			y = sb.toString();
		} else if (x.length() < y.length()) {
			/*
			 * prepend the diff of digits with 0 for x now add x n y
			 */
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < y.length() - x.length(); i++) {
				sb.append('0');
			}
			sb.append(x);
			x = sb.toString();
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
				diff.append(x1 - y1);
			} else if (x1 < y1) {
				borrow--;
				x1 = 10 + x1;
				diff.append(x1 - y1);
			}
		}

		diff = diff.reverse();
		String s1 = trimZero(diff.toString());

		if (isNegative) {
			return "-" + s1;
		} else {
			return s1;
		}
	}

	// trim 0's in the start of a string in diff
	public static String trimZero(String x) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < x.length(); i++) {
			if (x.charAt(i) != '0') {
				sb.append(x.substring(i));
				return sb.toString();
			}
		}
		return "0";
	}

	// karatsuba S5 function_append 0's
	public static String appendZero(String x, int length) {
		// S1 * (long) Math.pow(10, m * 2)//
		StringBuilder sb = new StringBuilder();
		sb.append(x);
		for (int i = 0; i < length; i++) {
			sb.append(0);
		}
		return sb.toString();
	}

	public static String generateRandomNumber(int digits) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < digits; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
}
