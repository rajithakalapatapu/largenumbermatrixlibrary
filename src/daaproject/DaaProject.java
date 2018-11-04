package daaproject;

import java.util.List;
import java.util.Scanner;

public class DaaProject {

	public static void main(String[] args) {
		/*
		 * largeMatrixGenerator(); String file1 = new String(
		 * "/home/rajitha/eclipse-workspace/daaproject/src/daaproject/matrix-1.txt");
		 * String file2 = new String(
		 * "/home/rajitha/eclipse-workspace/daaproject/src/daaproject/matrix-2.txt");
		 * readAndMultiplyLargeMatrices(file1, file2);
		 */

		largeNumberGenerator();
		readAndMultiplyLargeNumber("/home/rajitha/eclipse-workspace/daaproject/src/daaproject/large-numbers.txt");
	}

	private static void readAndMultiplyLargeNumber(String file) {
		LargeNumberReader largeNumberReader = new LargeNumberReader();
		List<LargeNumber> multiplicands = largeNumberReader.readFromFile(file);

		LargeNumber a = multiplicands.get(0);
		LargeNumber b = multiplicands.get(1);
		a.print();
		b.print();
//		System.out.println(a.multiply(b));
	}

	private static void readAndMultiplyLargeMatrices(String file1, String file2) {
		LargeMatrixReader largeMatrixReader = new LargeMatrixReader();
		LargeMatrix a = largeMatrixReader.parseFileContents(file1);
		a.print();

		LargeMatrix b = largeMatrixReader.parseFileContents(file2);
		b.print();

		LargeMatrix result = a.multiplyTraditional(b);
		result.print();

		LargeMatrix strassenResult = a.multiplyStrassens(b);
		strassenResult.print();

		System.out.println(result.isEqual(strassenResult));
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

		String inputFile = "/home/rajitha/eclipse-workspace/daaproject/src/daaproject/large-numbers.txt";

		System.out.println("Do you want to generate random multiplicands?");
		boolean generate = true; // scanner.nextBoolean();
		if (generate) {
			System.out.print("enter no. of digits each number should have: ");
			int digits = scanner.nextInt();
			System.out.print("How many such numbers do u want : ");
			int numbers = scanner.nextInt();
			LargeNumberGenerator largeNumberGenerator = new LargeNumberGenerator();
			largeNumberGenerator.writeToFile(inputFile, digits, numbers);
		}
		scanner.close();
	}

}
