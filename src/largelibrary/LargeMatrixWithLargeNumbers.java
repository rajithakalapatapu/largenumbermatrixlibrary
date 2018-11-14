package largelibrary;

import java.util.HashMap;

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

	public LargeMatrixWithLargeNumbers multiplyTraditional(LargeMatrixWithLargeNumbers other) {
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

	public boolean isEqual(LargeMatrixWithLargeNumbers other) {
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				if (this.dataAt(i, j).compareTo(other.dataAt(i, j)) != 0) {
//					System.out.println("whatsup?");
//					System.out.println(this.dataAt(i, j));
//					System.out.println(other.dataAt(i, j));
					return false;
				}
			}
		}
		return true;
	}

	public LargeMatrixWithLargeNumbers multiplyStrassens(LargeMatrixWithLargeNumbers other) {
		/* multiply this with other using Strassens algorithm */
		LargeMatrixWithLargeNumbers result = new LargeMatrixWithLargeNumbers(this.dimensions);

		if (this.dimensions == 1) {
			result.setDataAt(0, 0, 
					String.valueOf(Integer.valueOf(this.dataAt(0, 0)) * Integer.valueOf(other.dataAt(0, 0))));
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
			this.split(this12, 0, this.dimensions / 2);
			this.split(this21, this.dimensions / 2, 0);
			this.split(this22, this.dimensions / 2, this.dimensions / 2);

			other.split(other11, 0, 0);
			other.split(other12, 0, other.dimensions / 2);
			other.split(other21, other.dimensions / 2, 0);
			other.split(other22, other.dimensions / 2, other.dimensions / 2);

			LargeMatrixWithLargeNumbers m1 = this11.add(this22).multiplyStrassens(other11.add(other22));
			LargeMatrixWithLargeNumbers m2 = this21.add(this22).multiplyStrassens(other11);
			LargeMatrixWithLargeNumbers m3 = this11.multiplyStrassens(other12.subtract(other22));
			LargeMatrixWithLargeNumbers m4 = this22.multiplyStrassens(other21.subtract(other11));
			LargeMatrixWithLargeNumbers m5 = this11.add(this12).multiplyStrassens(other22);
			LargeMatrixWithLargeNumbers m6 = this21.subtract(this11).multiplyStrassens(other11.add(other12));
			LargeMatrixWithLargeNumbers m7 = this12.subtract(this22).multiplyStrassens(other21.add(other22));

			LargeMatrixWithLargeNumbers result11 = m1.add(m4).subtract(m5).add(m7);
			LargeMatrixWithLargeNumbers result12 = m3.add(m5);
			LargeMatrixWithLargeNumbers result21 = m2.add(m4);
			LargeMatrixWithLargeNumbers result22 = m1.add(m3).subtract(m2).add(m6);

			result11.join(result, 0, 0);
			result12.join(result, 0, result.dimensions / 2);
			result21.join(result, result.dimensions / 2, 0);
			result22.join(result, result.dimensions / 2, result.dimensions / 2);
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

}
