package largelibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DaaProject {

	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(System.in);

			StringBuilder sb = new StringBuilder();
			sb.append("What would you like to do? \n");
			sb.append("Press 1 for large number multiplication \n");
			sb.append("Press 2 for large matrix multiplication \n");
			sb.append("Press 3 for large matrix with large number multiplication \n");
			sb.append("Press 4 for all timing studies\n");
			sb.append("Press quit to stop testing \n");
			System.out.println(sb.toString());
			String enteredText = scanner.next();

			while (! enteredText.equalsIgnoreCase("quit") ) {
				Integer mode = Integer.valueOf(enteredText);
				switch (mode) {
				case 1:
					System.out.println("Enter the path to the file");
					String file = scanner.next();
					readAndMultiplyLargeNumber(file);
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					runTimingStudies();
					break;
				default:
					System.out.println("Invalid entry .... \n" + sb.toString());
				}
				enteredText = scanner.next();
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("Exception when opening file " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Exception when file IO" + e.getMessage());
		}
	}

	private static void runTimingStudies() throws IOException, FileNotFoundException {
		System.out.println("Starting timing studies for LargeNumber multiplication");
		LargeNumber.generateTimings();
		System.out.println("Completed timing studies for LargeNumber multiplication...");

		System.out.println("Starting timing studies for LargeMatrix multiplication");
		generateTimingsForMatrixMultiplication();
		System.out.println("Completed timing studies for LargeMatrix multiplication...");

		System.out.println("Done!");
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

	private static void readAndMultiplyLargeNumber(String file) throws FileNotFoundException {
		if (file.isEmpty()) {
			System.err.println("Invalid file specified");
		}

		LargeNumberReader largeNumberReader = new LargeNumberReader();
		List<String> multiplicands = largeNumberReader.readFromFile(file);

		System.out.println("Product via karatsuba algorithm is "
				+ LargeNumber.karatsuba(multiplicands.get(0), multiplicands.get(1)));

		System.out.println(
				"Product via gauss algorithm is " + LargeNumber.gauss(multiplicands.get(0), multiplicands.get(1)));

		System.out.println("Product via long multiplication algorithm is "
				+ LargeNumber.longMultiplication(multiplicands.get(0), multiplicands.get(1)));
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
