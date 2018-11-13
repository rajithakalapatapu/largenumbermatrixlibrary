package largelibrary;

import java.util.HashMap;
import java.util.Random;

public class LargeMatrix {

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

	private HashMap<Tuple, Integer> data = null;
	private int dimensions = 0;

	public LargeMatrix(int dimensions) {
		this.dimensions = dimensions;
		this.data = new HashMap<>();
	}

	private int dataAt(int xIndex, int yIndex) {
		Tuple index = new Tuple(xIndex, yIndex);
		if (data.containsKey(index)) {
			return data.get(index);
		}
		return 0;
	}

	private void setDataAt(int xIndex, int yIndex, int dataToSet) {
//        System.out.println(xIndex + "\t" + yIndex + "\t" + dataToSet);
		data.put(new Tuple(xIndex, yIndex), Integer.valueOf(dataToSet));
	}

	private LargeMatrix add(LargeMatrix other) {
		LargeMatrix result = new LargeMatrix(this.dimensions);
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				result.setDataAt(i, j, this.dataAt(i, j) + other.dataAt(i, j));
			}
		}
		return result;
	}

	private LargeMatrix subtract(LargeMatrix other) {
		LargeMatrix result = new LargeMatrix(this.dimensions);
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				result.setDataAt(i, j, this.dataAt(i, j) - other.dataAt(i, j));
			}
		}
		return result;
	}

	private LargeMatrix split(LargeMatrix child, int bound1, int bound2) {

		for (int p = 0, q = bound1; p < child.dimensions; p++, q++) {
			for (int r = 0, s = bound2; r < child.dimensions; r++, s++) {
//              System.out.println("setting child at " + p + " " + r + " with data from indexes parent " + q + " " + s + " data "+ this.dataAt(q, s));                
				child.setDataAt(p, r, this.dataAt(q, s));
			}
		}

		return child;
	}

	private LargeMatrix join(LargeMatrix parent, int bound1, int bound2) {

		for (int p = 0, q = bound1; p < this.dimensions; p++, q++) {
			for (int r = 0, s = bound2; r < this.dimensions; r++, s++) {
//				System.out.println("setting parent at " + q + " " + s + " with data from indexes child " + p + " " + r + " data "+ this.dataAt(p, r));
				parent.setDataAt(q, s, this.dataAt(p, r));
			}
		}

		return parent;
	}

	public void addElement(int x, int y, int data) {
		setDataAt(x, y, data);
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

	public LargeMatrix multiplyTraditional(LargeMatrix other) {
		/* multiply this with other using traditional n^3 algorithm */
		LargeMatrix result = new LargeMatrix(this.dimensions);
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				int sum = 0;
				for (int k = 0; k < dimensions; k++) {
					sum += this.dataAt(i, k) * other.dataAt(k, j);
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

		if (this.dimensions == 1) {
			result.setDataAt(0, 0, this.dataAt(0, 0) * other.dataAt(0, 0));
		} else {
			LargeMatrix this11 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix this12 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix this21 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix this22 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix other11 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix other12 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix other21 = new LargeMatrix(result.dimensions / 2);
			LargeMatrix other22 = new LargeMatrix(result.dimensions / 2);

			this.split(this11, 0, 0);
			this.split(this12, 0, this.dimensions / 2);
			this.split(this21, this.dimensions / 2, 0);
			this.split(this22, this.dimensions / 2, this.dimensions / 2);

			other.split(other11, 0, 0);
			other.split(other12, 0, other.dimensions / 2);
			other.split(other21, other.dimensions / 2, 0);
			other.split(other22, other.dimensions / 2, other.dimensions / 2);

			LargeMatrix m1 = this11.add(this22).multiplyStrassens(other11.add(other22));
			LargeMatrix m2 = this21.add(this22).multiplyStrassens(other11);
			LargeMatrix m3 = this11.multiplyStrassens(other12.subtract(other22));
			LargeMatrix m4 = this22.multiplyStrassens(other21.subtract(other11));
			LargeMatrix m5 = this11.add(this12).multiplyStrassens(other22);
			LargeMatrix m6 = this21.subtract(this11).multiplyStrassens(other11.add(other12));
			LargeMatrix m7 = this12.subtract(this22).multiplyStrassens(other21.add(other22));

			LargeMatrix result11 = m1.add(m4).subtract(m5).add(m7);
			LargeMatrix result12 = m3.add(m5);
			LargeMatrix result21 = m2.add(m4);
			LargeMatrix result22 = m1.add(m3).subtract(m2).add(m6);

			result11.join(result, 0, 0);
			result12.join(result, 0, result.dimensions / 2);
			result21.join(result, result.dimensions / 2, 0);
			result22.join(result, result.dimensions / 2, result.dimensions / 2);
		}

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
