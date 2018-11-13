package largelibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LargeMatrixGenerator {

	private int dimensions = 0;
	private int sparseness = 0;
	private boolean generateLargeNumbers = false;

	public LargeMatrixGenerator(int dimensions, int sparseness, boolean generateLargeNumbers) {
		this.dimensions = dimensions;
		this.sparseness = sparseness;
		this.generateLargeNumbers = generateLargeNumbers;
	}

	public List<String> generate(int digits) {
		List<String> generatedFiles = new ArrayList<>();

		System.out.println("Generating dense matrix of dimensions " + dimensions);

		String file1 = "matrix-1.txt";
		generateMatrix(file1, dimensions, sparseness, digits);
		generatedFiles.add(file1);

		String file2 = "matrix-2.txt";
		generateMatrix(file2, dimensions, sparseness, digits);
		generatedFiles.add(file2);

		return generatedFiles;
	}

	private void generateMatrix(String filePath, int dimensions, int sparseness, int digits) {
		Random random = new Random();
		File file = new File(filePath);
		FileWriter fr = null;
		try {
			fr = new FileWriter(file);

			int numberOfElements = (int) Math.pow(dimensions, 2);
			int maximumElementsToFill = (int) Math.ceil((sparseness / 100.0f) * numberOfElements);

			System.out.println(numberOfElements + "\t" + maximumElementsToFill);

			fr.write(String.valueOf(dimensions));
			fr.write("\n");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < maximumElementsToFill; i++) {
				sb.setLength(0);
				sb.append('(');
				sb.append(String.valueOf(random.nextInt(dimensions))); // row
				sb.append(',');
				sb.append(String.valueOf(random.nextInt(dimensions))); // column
				sb.append(',');
				if (this.generateLargeNumbers) {
					sb.append(LargeNumber.generateRandomNumber(digits) + "1");
				} else {
					sb.append(String.valueOf(random.nextInt(10) + 1)); // value of element at row, column - avoid generating 0
				}
				sb.append(')');
				sb.append('\n');
				fr.write(sb.toString());
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

}
