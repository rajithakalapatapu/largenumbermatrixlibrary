package daaproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DaaProject {

	public static void main(String[] args) {
        try {
			System.out.println("Starting timing studies for LargeNumber multiplication");
			LargeNumber.generateTimings();
			System.out.println("Completed timing studies for LargeNumber multiplication...");

			System.out.println("Starting timing studies for LargeMatrix multiplication");
			generateTimingsForMatrixMultiplication();
			System.out.println("Completed timing studies for LargeMatrix multiplication...");
			
			System.out.println("Done!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void generateTimingsForMatrixMultiplication() {
		int power = 5; // set this to a maximum of 13 or 14 - don't go beyond that.

		File file = new File("large_matrix_results.csv");
		FileWriter fr = null;
		StringBuilder sb = new StringBuilder();

		try {
			fr = new FileWriter(file);
			sb.append("dimensions");
			sb.append(",");
			sb.append("sparseness");
			sb.append(",");
			sb.append("Traditional Multiplication - time taken (milli seconds)");
			sb.append(",");
			sb.append("Strassens Multiplication - time taken (milli seconds)");
			sb.append("\n");

			int[] sparsenessValues = { 10, 25, 50, 75, 100 };
			for (int sparseness : sparsenessValues) {
				for (int i = 0; i <= power; i++) {
					int dimensions = (int) Math.pow(2, i);

					System.out.println("Creating a " + dimensions + "x" + dimensions + " matrix - with only "
							+ sparseness + "% of entries filled");
					LargeMatrixGenerator largeMatrixGenerator = new LargeMatrixGenerator(dimensions, sparseness);
					List<String> filesGenerated = largeMatrixGenerator.generate();

					long startTime = System.currentTimeMillis();
					readAndMultiplyLargeMatricesTraditional(filesGenerated.get(0), filesGenerated.get(1));
					long endTime = System.currentTimeMillis();
					long traditionalDuration = endTime - startTime;

					startTime = System.currentTimeMillis();
					readAndMultiplyLargeMatricesStrassens(filesGenerated.get(0), filesGenerated.get(1));
					endTime = System.currentTimeMillis();
					long strassensDuration = endTime - startTime;

					sb.append(dimensions);
					sb.append(",");
					sb.append(sparseness);
					sb.append(",");
					sb.append(traditionalDuration);
					sb.append(",");
					sb.append(strassensDuration);
					sb.append("\n");

					fr.write(sb.toString());
					sb.setLength(0);
				}
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

	private static void readAndMultiplyLargeNumber(String file) {
		LargeNumberReader largeNumberReader = new LargeNumberReader();
		List<LargeNumberOld> multiplicands = largeNumberReader.readFromFile(file);

		LargeNumberOld a = multiplicands.get(0);
		LargeNumberOld b = multiplicands.get(1);
		a.print();
		System.out.println(a.numberOfDigits());
		b.print();
		System.out.println(b.numberOfDigits());
		a.add(b).print();

//		System.out.println(a.multiply(b));
	}

	private static void readAndMultiplyLargeMatricesTraditional(String file1, String file2) {
		LargeMatrixReader largeMatrixReader = new LargeMatrixReader();
		LargeMatrix a = largeMatrixReader.parseFileContents(file1);
//		a.print();

		LargeMatrix b = largeMatrixReader.parseFileContents(file2);
//		b.print();

		LargeMatrix result = a.multiplyTraditional(b);
		result.print();

	}

	private static void readAndMultiplyLargeMatricesStrassens(String file1, String file2) {
		LargeMatrixReader largeMatrixReader = new LargeMatrixReader();
		LargeMatrix a = largeMatrixReader.parseFileContents(file1);
//		a.print();

		LargeMatrix b = largeMatrixReader.parseFileContents(file2);
//		b.print();

		LargeMatrix result = a.multiplyStrassens(b);
		result.print();

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

		String inputFile = "large-numbers.txt";

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
