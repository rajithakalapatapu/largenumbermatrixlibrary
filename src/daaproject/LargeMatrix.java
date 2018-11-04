package daaproject;

import java.util.Random;

public class LargeMatrix {

	private int[][] data;
	private int dimensions = 0;

	public LargeMatrix(int dimensions) {
		this.dimensions = dimensions;
		this.data = new int[this.dimensions][this.dimensions];
	}

	private int dataAt(int xIndex, int yIndex) {
		return data[xIndex][yIndex];
	}

	private void setDataAt(int xIndex, int yIndex, int dataToSet) {
		data[xIndex][yIndex] = dataToSet;
	}

	public void print() {
		System.out.println("Printing matrix data");
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				System.out.print(this.dataAt(i,j) + "\t");
			}
			System.out.println("\n");
		}
	}

	public LargeMatrix multiplyTraditional(LargeMatrix other) {
		/* multiply this with other using traditional n^3 algorithm */
		LargeMatrix result = new LargeMatrix(this.dimensions);
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				int sum = 0;
				for (int k = 0; k < dimensions; k++) {
					sum += this.dataAt(i,k) * other.dataAt(k,j);
				}
				result.setDataAt(i, j, sum);
			}
		}
		return result;
	}

	public boolean isEqual(LargeMatrix other) {
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				if (this.dataAt(i, j) != other.dataAt(i, j))
					return false;
			}
		}
		return true;
	}

	public LargeMatrix multiplyStrassens(LargeMatrix other) {
		/* multiply this with other using Strassens algorithm */
		LargeMatrix result = new LargeMatrix(this.dimensions);

/*		if(this.dimensions == 1) {
			result.data[0][0] = this.data[0][0] * other.data[0][0];
		} else {
			LargeMatrix this11 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix this12 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix this21 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix this22 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix other11 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix other12 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix other21 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix other22 = new LargeMatrix(result.dimensions / 2);
		}
*/			
		return result;
	}

	public void fillRandomValues() {
		System.out.println("Filling random values in to matrix data");
		Random random = new Random();
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				this.setDataAt(i, j, random.nextInt(10));
			}
		}
	}

}
