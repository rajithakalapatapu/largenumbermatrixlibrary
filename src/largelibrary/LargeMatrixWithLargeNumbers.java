package largelibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LargeMatrixWithLargeNumbers {

	public class Tuple {
		final private int xIndex;
		final private int yIndex;

		public Tuple(int x, int y) {
			this.xIndex = x;
			this.yIndex = y;
		}

		@Override
		public int hashCode() {
			return xIndex ^ yIndex;
		}

		@Override
		public boolean equals(Object obj) {
			return (obj instanceof Tuple) && ((Tuple) obj).xIndex == this.xIndex && ((Tuple) obj).yIndex == this.yIndex;
		}
	}

	private HashMap<Tuple, String> data = null;
	private int dimensions = 0;

	public LargeMatrixWithLargeNumbers(int dimensions) {
		this.dimensions = dimensions;
		this.data = new HashMap<>(dimensions);
	}

	private String dataAt(int xIndex, int yIndex) {
		Tuple index = new Tuple(xIndex, yIndex);
		if (data.containsKey(index)) {
			return data.get(index);
		}
		return "0";
	}

	private void setDataAt(int xIndex, int yIndex, String largeValue) {
//        System.out.println(xIndex + "\t" + yIndex + "\t" + largeValue);
		data.put(new Tuple(xIndex, yIndex), largeValue);
	}

	public LargeMatrixWithLargeNumbers add(LargeMatrixWithLargeNumbers other) {
		LargeMatrixWithLargeNumbers result = new LargeMatrixWithLargeNumbers(this.dimensions);
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				result.setDataAt(i, j, LargeNumber.sumString(this.dataAt(i, j), other.dataAt(i, j)));
			}
		}
		return result;
	}

	public LargeMatrixWithLargeNumbers subtract(LargeMatrixWithLargeNumbers other) {
		LargeMatrixWithLargeNumbers result = new LargeMatrixWithLargeNumbers(this.dimensions);
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				result.setDataAt(i, j, LargeNumber.diffString(this.dataAt(i, j), other.dataAt(i, j)));
			}
		}
		return result;
	}

	public LargeMatrixWithLargeNumbers split(LargeMatrixWithLargeNumbers child, int bound1, int bound2) {

		for (int p = 0, q = bound1; p < child.dimensions; p++, q++) {
			for (int r = 0, s = bound2; r < child.dimensions; r++, s++) {
//              System.out.println("setting child at " + p + " " + r + " with data from indexes parent " + q + " " + s + " data "+ this.dataAt(q, s));                
				child.setDataAt(p, r, this.dataAt(q, s));
			}
		}

		return child;
	}

	public LargeMatrixWithLargeNumbers join(LargeMatrixWithLargeNumbers parent, int bound1, int bound2) {

		for (int p = 0, q = bound1; p < this.dimensions; p++, q++) {
			for (int r = 0, s = bound2; r < this.dimensions; r++, s++) {
//				System.out.println("setting parent at " + q + " " + s + " with data from indexes child " + p + " " + r + " data "+ this.dataAt(p, r));
				parent.setDataAt(q, s, this.dataAt(p, r));
			}
		}

		return parent;
	}

	public void addElement(int x, int y, String largeValue) {
		setDataAt(x, y, largeValue);
	}

	public void print() {
		System.out.println("Printing matrix data");
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				System.out.print(this.dataAt(i, j) + "\t");
			}
			System.out.println("\n");
		}
	}

	private static List<LargeMatrixWithLargeNumbers> readLargeMatricesWithLargeNumbers(String file1, String file2) {
		List<LargeMatrixWithLargeNumbers> output = new ArrayList<>();
		LargeMatrixWithLargeNumbersReader largeMatrixWithLargeNumbersReader = new LargeMatrixWithLargeNumbersReader();
		LargeMatrixWithLargeNumbers a = largeMatrixWithLargeNumbersReader.parseFileContents(file1);
		output.add(a);
		LargeMatrixWithLargeNumbers b = largeMatrixWithLargeNumbersReader.parseFileContents(file2);
		output.add(b);
		return output;
	}

	public static void generateAllSixTimings() {
		int power = 5; // set this to a maximum of 13 or 14 - don't go beyond that.

		File file = new File("large_matrix_with_large_numbers_results.csv");
		FileWriter fr = null;
		StringBuilder sb = new StringBuilder();

		try {
			fr = new FileWriter(file);
			sb.append("dimensions");
			sb.append(",");
			sb.append("sparseness");
			sb.append(",");
			sb.append("Traditional Multiplication + Long Multiplication - time taken (milli seconds)");
			sb.append(",");
			sb.append("Traditional Multiplication + Gauss - time taken (milli seconds)");
			sb.append(",");
			sb.append("Traditional Multiplication + Karatsuba - time taken (milli seconds)");
			sb.append(",");
			sb.append("Strassens Multiplication + Long Multiplication - time taken (milli seconds)");
			sb.append(",");
			sb.append("Strassens Multiplication + Gauss - time taken (milli seconds)");
			sb.append(",");
			sb.append("Strassens Multiplication + Karatsuba - time taken (milli seconds)");
			sb.append("\n");

			int[] sparsenessValues = { 10, 25, 50, 75, 100 };
			for (int sparseness : sparsenessValues) {
				for (int i = 0; i <= power; i++) {
					int dimensions = (int) Math.pow(2, i);

					System.out.println("Creating a " + dimensions + "x" + dimensions + " matrix - with only "
							+ sparseness + "% of entries filled");
					boolean generateLargeNumbers = true;
					LargeMatrixGenerator largeMatrixGenerator = new LargeMatrixGenerator(dimensions, sparseness,
							generateLargeNumbers);
					List<String> filesGenerated = largeMatrixGenerator.generate(5);

					List<LargeMatrixWithLargeNumbers> matrices = readLargeMatricesWithLargeNumbers(
							filesGenerated.get(0), filesGenerated.get(1));

					long startTime = System.currentTimeMillis();
					matrices.get(0).multiplyTraditionalLongMultiplication(matrices.get(1));
					long endTime = System.currentTimeMillis();
					long tradLongMult = endTime - startTime;

					startTime = System.currentTimeMillis();
					matrices.get(0).multiplyTraditionalGauss(matrices.get(1));
					endTime = System.currentTimeMillis();
					long tradGauss = endTime - startTime;

					startTime = System.currentTimeMillis();
					matrices.get(0).multiplyTraditionalKaratsuba(matrices.get(1));
					endTime = System.currentTimeMillis();
					long tradKaratsuba = endTime - startTime;

					startTime = System.currentTimeMillis();
					matrices.get(0).multiplyStrassensLongMultiplication(matrices.get(1));
					endTime = System.currentTimeMillis();
					long strassensLM = endTime - startTime;

					startTime = System.currentTimeMillis();
					matrices.get(0).multiplyStrassensGauss(matrices.get(1));
					endTime = System.currentTimeMillis();
					long strassensGauss = endTime - startTime;

					startTime = System.currentTimeMillis();
					matrices.get(0).multiplyStrassensKaratsuba(matrices.get(1));
					endTime = System.currentTimeMillis();
					long strassensKaratsuba = endTime - startTime;

					sb.append(dimensions);
					sb.append(",");
					sb.append(sparseness);
					sb.append(",");
					sb.append(tradLongMult);
					sb.append(",");
					sb.append(tradGauss);
					sb.append(",");
					sb.append(tradKaratsuba);
					sb.append(",");
					sb.append(strassensLM);
					sb.append(",");
					sb.append(strassensGauss);
					sb.append(",");
					sb.append(strassensKaratsuba);
					sb.append(",");
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

	public LargeMatrixWithLargeNumbers multiplyTraditionalKaratsuba(LargeMatrixWithLargeNumbers other) {
		/* multiply this with other using traditional n^3 algorithm */
		LargeMatrixWithLargeNumbers result = new LargeMatrixWithLargeNumbers(this.dimensions);
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				String sum = "0";
				for (int k = 0; k < dimensions; k++) {
					sum = LargeNumber.sumString(sum, LargeNumber.karatsuba(this.dataAt(i, k), other.dataAt(k, j)));
				}
				result.setDataAt(i, j, sum);
			}
		}
		return result;
	}

	public LargeMatrixWithLargeNumbers multiplyTraditionalGauss(LargeMatrixWithLargeNumbers other) {
		/* multiply this with other using traditional n^3 algorithm */
		LargeMatrixWithLargeNumbers result = new LargeMatrixWithLargeNumbers(this.dimensions);
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				String sum = "0";
				for (int k = 0; k < dimensions; k++) {
					sum = LargeNumber.sumString(sum, LargeNumber.gauss(this.dataAt(i, k), other.dataAt(k, j)));
				}
				result.setDataAt(i, j, sum);
			}
		}
		return result;
	}

	public LargeMatrixWithLargeNumbers multiplyTraditionalLongMultiplication(LargeMatrixWithLargeNumbers other) {
		/* multiply this with other using traditional n^3 algorithm */
		LargeMatrixWithLargeNumbers result = new LargeMatrixWithLargeNumbers(this.dimensions);
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				String sum = "0";
				for (int k = 0; k < dimensions; k++) {
//					System.out.println("left = " + this.dataAt(i, k) + " right = " + other.dataAt(k, j));
					sum = LargeNumber.sumString(sum,
							LargeNumber.longMultiplication(this.dataAt(i, k), other.dataAt(k, j)));
				}
				result.setDataAt(i, j, sum);
			}
		}
		return result;
	}

	public boolean isEqual(LargeMatrixWithLargeNumbers other) {
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				if (!this.dataAt(i, j).equalsIgnoreCase(other.dataAt(i, j))) {
//					System.out.println("whatsup?");
//					System.out.println(this.dataAt(i, j));
//					System.out.println(other.dataAt(i, j));
					return false;
				}
			}
		}
		return true;
	}

	public LargeMatrixWithLargeNumbers multiplyStrassensKaratsuba(LargeMatrixWithLargeNumbers other) {
		/* multiply this with other using Strassens algorithm */
		LargeMatrixWithLargeNumbers result = new LargeMatrixWithLargeNumbers(this.dimensions);

		if (this.dimensions == 1) {
//			System.out.println("dimensions are 1 - base case " + this.dataAt(0, 0) + "\t" + other.dataAt(0, 0));
			result.setDataAt(0, 0, LargeNumber.karatsuba(this.dataAt(0, 0), other.dataAt(0, 0)));
//			System.out.println(result.dataAt(0, 0));
		} else {
			LargeMatrixWithLargeNumbers this11 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers this12 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers this21 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers this22 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other11 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other12 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other21 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other22 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);

			this.split(this11, 0, 0);
//			System.out.println("Splitting at 0, 0");
//			this11.print();
			this.split(this12, 0, this.dimensions / 2);
//			System.out.println("Splitting at " + "0," + this.dimensions / 2);
//			this12.print();
			this.split(this21, this.dimensions / 2, 0);
//			System.out.println("Splitting at " + this.dimensions / 2 + "0");
//			this21.print();
			this.split(this22, this.dimensions / 2, this.dimensions / 2);
//			System.out.println("Splitting at " + this.dimensions / 2 + this.dimensions / 2);
//			this22.print();

			other.split(other11, 0, 0);
//			System.out.println("Splitting at 0, 0");
//			other11.print();
			other.split(other12, 0, other.dimensions / 2);
//			System.out.println("Splitting at " + "0," + other.dimensions / 2);
//			other12.print();
			other.split(other21, other.dimensions / 2, 0);
//			System.out.println("Splitting at " + other.dimensions / 2 + "0");
//			other21.print();
			other.split(other22, other.dimensions / 2, other.dimensions / 2);
//			System.out.println("Splitting at " + other.dimensions / 2 + other.dimensions / 2);
//			other22.print();

			LargeMatrixWithLargeNumbers m1 = this11.add(this22).multiplyStrassensKaratsuba(other11.add(other22));
			LargeMatrixWithLargeNumbers m2 = this21.add(this22).multiplyStrassensKaratsuba(other11);
			LargeMatrixWithLargeNumbers m3 = this11.multiplyStrassensKaratsuba(other12.subtract(other22));
			LargeMatrixWithLargeNumbers m4 = this22.multiplyStrassensKaratsuba(other21.subtract(other11));
			LargeMatrixWithLargeNumbers m5 = this11.add(this12).multiplyStrassensKaratsuba(other22);
			LargeMatrixWithLargeNumbers m6 = this21.subtract(this11).multiplyStrassensKaratsuba(other11.add(other12));
			LargeMatrixWithLargeNumbers m7 = this12.subtract(this22).multiplyStrassensKaratsuba(other21.add(other22));

//			System.out.println("all m matrices print now");
//			m1.print();
//			m2.print();
//			m3.print();
//			m4.print();
//			m5.print();
//			m6.print();
//			m7.print();
//			System.out.println("all m matrices print DONE");

			LargeMatrixWithLargeNumbers result11 = m1.add(m4).subtract(m5).add(m7);
			LargeMatrixWithLargeNumbers result12 = m3.add(m5);
			LargeMatrixWithLargeNumbers result21 = m2.add(m4);
			LargeMatrixWithLargeNumbers result22 = m1.add(m3).subtract(m2).add(m6);

//			System.out.println("all result matrices print now");
//			result11.print();
//			result12.print();
//			result21.print();
//			result22.print();
//			System.out.println("all result matrices print DONE");

			result11.join(result, 0, 0);
			result12.join(result, 0, result.dimensions / 2);
			result21.join(result, result.dimensions / 2, 0);
			result22.join(result, result.dimensions / 2, result.dimensions / 2);

//			System.out.println("final result");
//			result.print();
		}

		return result;
	}

	public LargeMatrixWithLargeNumbers multiplyStrassensLongMultiplication(LargeMatrixWithLargeNumbers other) {
		/* multiply this with other using Strassens algorithm */
		LargeMatrixWithLargeNumbers result = new LargeMatrixWithLargeNumbers(this.dimensions);

		if (this.dimensions == 1) {
//			System.out.println("dimensions are 1 - base case " + this.dataAt(0, 0) + "\t" + other.dataAt(0, 0));
			result.setDataAt(0, 0, LargeNumber.longMultiplication(this.dataAt(0, 0), other.dataAt(0, 0)));
//			System.out.println(result.dataAt(0, 0));
		} else {
			LargeMatrixWithLargeNumbers this11 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers this12 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers this21 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers this22 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other11 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other12 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other21 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other22 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);

			this.split(this11, 0, 0);
//			System.out.println("Splitting at 0, 0");
//			this11.print();
			this.split(this12, 0, this.dimensions / 2);
//			System.out.println("Splitting at " + "0," + this.dimensions / 2);
//			this12.print();
			this.split(this21, this.dimensions / 2, 0);
//			System.out.println("Splitting at " + this.dimensions / 2 + "0");
//			this21.print();
			this.split(this22, this.dimensions / 2, this.dimensions / 2);
//			System.out.println("Splitting at " + this.dimensions / 2 + this.dimensions / 2);
//			this22.print();

			other.split(other11, 0, 0);
//			System.out.println("Splitting at 0, 0");
//			other11.print();
			other.split(other12, 0, other.dimensions / 2);
//			System.out.println("Splitting at " + "0," + other.dimensions / 2);
//			other12.print();
			other.split(other21, other.dimensions / 2, 0);
//			System.out.println("Splitting at " + other.dimensions / 2 + "0");
//			other21.print();
			other.split(other22, other.dimensions / 2, other.dimensions / 2);
//			System.out.println("Splitting at " + other.dimensions / 2 + other.dimensions / 2);
//			other22.print();

			LargeMatrixWithLargeNumbers m1 = this11.add(this22)
					.multiplyStrassensLongMultiplication(other11.add(other22));
			LargeMatrixWithLargeNumbers m2 = this21.add(this22).multiplyStrassensLongMultiplication(other11);
			LargeMatrixWithLargeNumbers m3 = this11.multiplyStrassensLongMultiplication(other12.subtract(other22));
			LargeMatrixWithLargeNumbers m4 = this22.multiplyStrassensLongMultiplication(other21.subtract(other11));
			LargeMatrixWithLargeNumbers m5 = this11.add(this12).multiplyStrassensLongMultiplication(other22);
			LargeMatrixWithLargeNumbers m6 = this21.subtract(this11)
					.multiplyStrassensLongMultiplication(other11.add(other12));
			LargeMatrixWithLargeNumbers m7 = this12.subtract(this22)
					.multiplyStrassensLongMultiplication(other21.add(other22));

//			System.out.println("all m matrices print now");
//			m1.print();
//			m2.print();
//			m3.print();
//			m4.print();
//			m5.print();
//			m6.print();
//			m7.print();
//			System.out.println("all m matrices print DONE");

			LargeMatrixWithLargeNumbers result11 = m1.add(m4).subtract(m5).add(m7);
			LargeMatrixWithLargeNumbers result12 = m3.add(m5);
			LargeMatrixWithLargeNumbers result21 = m2.add(m4);
			LargeMatrixWithLargeNumbers result22 = m1.add(m3).subtract(m2).add(m6);

//			System.out.println("all result matrices print now");
//			result11.print();
//			result12.print();
//			result21.print();
//			result22.print();
//			System.out.println("all result matrices print DONE");

			result11.join(result, 0, 0);
			result12.join(result, 0, result.dimensions / 2);
			result21.join(result, result.dimensions / 2, 0);
			result22.join(result, result.dimensions / 2, result.dimensions / 2);

//			System.out.println("final result");
//			result.print();
		}

		return result;
	}

	public void fillRandomValues() {
		System.out.println("Filling random values in to matrix data");
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				this.setDataAt(i, j, LargeNumber.generateRandomNumber(dimensions));
			}
		}
	}

	public LargeMatrixWithLargeNumbers multiplyStrassensGauss(LargeMatrixWithLargeNumbers other) {
		/* multiply this with other using Strassens algorithm */
		LargeMatrixWithLargeNumbers result = new LargeMatrixWithLargeNumbers(this.dimensions);

		if (this.dimensions == 1) {
			// System.out.println("dimensions are 1 - base case " + this.dataAt(0, 0) + "\t"
			// + other.dataAt(0, 0));
			result.setDataAt(0, 0, LargeNumber.gauss(this.dataAt(0, 0), other.dataAt(0, 0)));
			// System.out.println(result.dataAt(0, 0));
		} else {
			LargeMatrixWithLargeNumbers this11 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers this12 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers this21 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers this22 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other11 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other12 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other21 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);
			LargeMatrixWithLargeNumbers other22 = new LargeMatrixWithLargeNumbers(result.dimensions / 2);

			this.split(this11, 0, 0);
			// System.out.println("Splitting at 0, 0");
			// this11.print();
			this.split(this12, 0, this.dimensions / 2);
			// System.out.println("Splitting at " + "0," + this.dimensions / 2);
			// this12.print();
			this.split(this21, this.dimensions / 2, 0);
			// System.out.println("Splitting at " + this.dimensions / 2 + "0");
			// this21.print();
			this.split(this22, this.dimensions / 2, this.dimensions / 2);
			// System.out.println("Splitting at " + this.dimensions / 2 + this.dimensions /
			// 2);
			// this22.print();

			other.split(other11, 0, 0);
			// System.out.println("Splitting at 0, 0");
			// other11.print();
			other.split(other12, 0, other.dimensions / 2);
			// System.out.println("Splitting at " + "0," + other.dimensions / 2);
			// other12.print();
			other.split(other21, other.dimensions / 2, 0);
			// System.out.println("Splitting at " + other.dimensions / 2 + "0");
			// other21.print();
			other.split(other22, other.dimensions / 2, other.dimensions / 2);
			// System.out.println("Splitting at " + other.dimensions / 2 + other.dimensions
			// / 2);
			// other22.print();

			LargeMatrixWithLargeNumbers m1 = this11.add(this22).multiplyStrassensGauss(other11.add(other22));
			LargeMatrixWithLargeNumbers m2 = this21.add(this22).multiplyStrassensGauss(other11);
			LargeMatrixWithLargeNumbers m3 = this11.multiplyStrassensGauss(other12.subtract(other22));
			LargeMatrixWithLargeNumbers m4 = this22.multiplyStrassensGauss(other21.subtract(other11));
			LargeMatrixWithLargeNumbers m5 = this11.add(this12).multiplyStrassensGauss(other22);
			LargeMatrixWithLargeNumbers m6 = this21.subtract(this11).multiplyStrassensGauss(other11.add(other12));
			LargeMatrixWithLargeNumbers m7 = this12.subtract(this22).multiplyStrassensGauss(other21.add(other22));

			// System.out.println("all m matrices print now");
			// m1.print();
			// m2.print();
			// m3.print();
			// m4.print();
			// m5.print();
			// m6.print();
			// m7.print();
			// System.out.println("all m matrices print DONE");

			LargeMatrixWithLargeNumbers result11 = m1.add(m4).subtract(m5).add(m7);
			LargeMatrixWithLargeNumbers result12 = m3.add(m5);
			LargeMatrixWithLargeNumbers result21 = m2.add(m4);
			LargeMatrixWithLargeNumbers result22 = m1.add(m3).subtract(m2).add(m6);

			// System.out.println("all result matrices print now");
			// result11.print();
			// result12.print();
			// result21.print();
			// result22.print();
			// System.out.println("all result matrices print DONE");

			result11.join(result, 0, 0);
			result12.join(result, 0, result.dimensions / 2);
			result21.join(result, result.dimensions / 2, 0);
			result22.join(result, result.dimensions / 2, result.dimensions / 2);

			// System.out.println("final result");
			// result.print();
		}

		return result;
	}

}
