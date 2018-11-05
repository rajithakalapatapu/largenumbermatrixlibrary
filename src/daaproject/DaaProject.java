package daaproject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DaaProject {

	public static void main(String[] args) {

		suchitraRunThis();

//		largeMatrixGenerator();
//		String file1 = new String("/home/rajitha/eclipse-workspace/daaproject/src/daaproject/matrix-1.txt");
//		String file2 = new String("/home/rajitha/eclipse-workspace/daaproject/src/daaproject/matrix-2.txt");
//		readAndMultiplyLargeMatrices(file1, file2);

//		largeNumberGenerator();
//		readAndMultiplyLargeNumber("/home/rajitha/eclipse-workspace/daaproject/src/daaproject/large-numbers.txt");
	}

	private static void suchitraRunThis() {
		int power = 5; // set this to a maximum of 13 or 14 - don't go beyond that.

		File file = new File("large_number_results.csv");
		FileWriter fr = null;
		StringBuilder sb = new StringBuilder();

		try {
			fr = new FileWriter(file);
			fr.write("Traditional matrix multiplication\n");
			sb.append("dimensions");
			sb.append(",");
			sb.append("sparseness");
			sb.append(",");
			sb.append("time taken (nano seconds)");
			sb.append("\n");

			fr.write(sb.toString());
			sb.setLength(0);

			int[] sparsenessValues = { 10, 25, 50, 75, 100 };
			for (int sparseness : sparsenessValues) {
				for (int i = 0; i < power; i++) {
					int dimensions = (int) Math.pow(2, i);

					System.out.println("Creating a " + dimensions + "x" + dimensions + " matrix - with only "
							+ sparseness + "% of entries filled");
					LargeMatrixGenerator largeMatrixGenerator = new LargeMatrixGenerator(dimensions, sparseness);
					List<String> filesGenerated = largeMatrixGenerator.generate();

					long startTime = System.nanoTime();
					readAndMultiplyLargeMatricesTraditional(filesGenerated.get(0), filesGenerated.get(1));
					long endTime = System.nanoTime();

					long duration = endTime - startTime;

					sb.append(dimensions + "x" + dimensions);
					sb.append(",");
					sb.append(sparseness);
					sb.append(",");
					sb.append(duration);
					sb.append("\n");

					fr.write(sb.toString());
					sb.setLength(0);
				}
			}

			fr.write("Strassens matrix multiplication\n");
			sb.append("dimensions");
			sb.append(",");
			sb.append("sparseness");
			sb.append(",");
			sb.append("time taken (nano seconds)");
			sb.append("\n");

			fr.write(sb.toString());
			sb.setLength(0);

			for (int sparseness : sparsenessValues) {
				for (int i = 0; i < power; i++) {
					int dimensions = (int) Math.pow(2, i);

					System.out.println("Creating a " + dimensions + "x" + dimensions + " matrix - with only "
							+ sparseness + "% of entries filled");
					LargeMatrixGenerator largeMatrixGenerator = new LargeMatrixGenerator(dimensions, sparseness);
					List<String> filesGenerated = largeMatrixGenerator.generate();

					long startTime = System.nanoTime();
					readAndMultiplyLargeMatricesStrassens(filesGenerated.get(0), filesGenerated.get(1));
					long endTime = System.nanoTime();

					long duration = endTime - startTime;

					sb.append(dimensions + "x" + dimensions);
					sb.append(",");
					sb.append(sparseness);
					sb.append(",");
					sb.append(duration);
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
		List<LargeNumber> multiplicands = largeNumberReader.readFromFile(file);

		LargeNumber a = multiplicands.get(0);
		LargeNumber b = multiplicands.get(1);
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
//		result.print();

	}

	private static void readAndMultiplyLargeMatricesStrassens(String file1, String file2) {
		LargeMatrixReader largeMatrixReader = new LargeMatrixReader();
		LargeMatrix a = largeMatrixReader.parseFileContents(file1);
//		a.print();

		LargeMatrix b = largeMatrixReader.parseFileContents(file2);
//		b.print();

		LargeMatrix result = a.multiplyStrassens(b);
//		result.print();

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
