package daaproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LargeNumberInputGenerator {

	public static void main(String[] args) {

		// largeNumberGenerator();
		largeMatrixGenerator();

	}

	private static void largeMatrixGenerator() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("How many dimensions would you like (should be a power of 2)");
		int dimensions = scanner.nextInt();

		System.out.println("What % of entries should be filled?");
		int sparseness = scanner.nextInt();

		LargeMatrixGenerator largeMatrixGenerator = new LargeMatrixGenerator(dimensions, sparseness);
		List<String> filesGenerated = largeMatrixGenerator.generate();
		System.out.println("Generated files are ");
		for (String file : filesGenerated) {
			System.out.println(file);
		}
		scanner.close();
	}

	private static void largeNumberGenerator() {
		Scanner scanner = new Scanner(System.in);

		String inputFile = "/home/rajitha/eclipse-workspace/daaproject/src/daaproject/shohed.txt";

		System.out.println("Do you want to generate random multiplicands?");
		boolean generate = true; // scanner.nextBoolean();
		if (generate) {
			System.out.print("enter no. of digits each number should have: ");
			int digits = scanner.nextInt();
			System.out.print("How many such numbers do u want : ");
			int numbers = scanner.nextInt();
			writeToFile(inputFile, digits, numbers);
		}
		scanner.close();

		List<BigInteger> multiplicands = readFromFile(inputFile);
		for (BigInteger b : multiplicands) {
			System.out.println(b);
		}

		BigInteger a = multiplicands.get(0);
		BigInteger b = multiplicands.get(1);
		System.out.println(a.multiply(b));
	}

	private static void writeToFile(String filePath, int digits, int numbers) {
		File file = new File(filePath);
		FileWriter fr = null;
		try {
			fr = new FileWriter(file);
			for (int i = 0; i < numbers; i++) {
				fr.write(String.valueOf(generateLargeNumber(digits)));
				fr.write("\n");
			}
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

	private static BigInteger generateLargeNumber(int digits) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < digits; i++) {
			sb.append(random.nextInt(10));
		}
		String generatedNumber = sb.toString();
		BigInteger b = new BigInteger(generatedNumber);
		return b;
	}

	private static List<BigInteger> readFromFile(String filepath) {
		File file = new File(filepath);

		List<BigInteger> multiplicands = new ArrayList<>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String st;
		try {
			while ((st = br.readLine()) != null) {
				multiplicands.add(new BigInteger(st));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return multiplicands;
	}

}
