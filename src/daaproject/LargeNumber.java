package daaproject;

public class LargeNumber {

	int signOfNumber;
	int[] digits;

	public LargeNumber() {

	}

	public LargeNumber(String largeNumberAsString) {
		if (largeNumberAsString.charAt(0) == '-') {
			signOfNumber = -1;
		} else if (largeNumberAsString.charAt(0) == '0') {
			signOfNumber = 0;
		} else {
			signOfNumber = 1;
		}

		digits = new int[largeNumberAsString.length()];
		for (int i = 0; i < largeNumberAsString.length(); i++) {
			digits[i] = largeNumberAsString.charAt(i) - '0';
		}
	}

	public void print() {
		for (int i = 0; i < digits.length; i++) {
			System.out.print(digits[i]);
		}
		System.out.println("\n");
	}
}
