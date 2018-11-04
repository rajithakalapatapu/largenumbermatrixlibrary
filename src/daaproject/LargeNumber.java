package daaproject;

public class LargeNumber {

	int signOfNumber;
	int[] digits;

	public LargeNumber() {
	}

	public LargeNumber(int length) {
		digits = new int[length];
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

	public int numberOfDigits() {
		return digits.length;
	}

	public LargeNumber add(LargeNumber other) {
		LargeNumber result = new LargeNumber(Math.max(this.numberOfDigits(), other.numberOfDigits()));
//		System.out.println(result.numberOfDigits());

		int k = result.numberOfDigits() - 1;
		int carry = 0;
		int i, j;
		for(i = this.numberOfDigits() - 1, j = other.numberOfDigits() - 1; i >= 0 && j >= 0; i--, j--) {
			int sum = this.digits[i] + other.digits[j] + carry;
			result.digits[k] = sum % 10;
			carry = sum / 10;
//			System.out.println("i - " + i + " j - " + j + " k - " + k + " digits " + sum % 10 + " carry " + carry);
			k--;
		}

		if (i < 0) {
			while(j-- > 0) {
				int sum = other.digits[j] + carry;
				result.digits[k--] = sum % 10;
				carry = sum / 10;
			}
		} else if (j < 0) {
			while (i-- > 0) {
				int sum = this.digits[i] + carry;
				result.digits[k--] = sum % 10;
				carry = sum / 10;
			}
		}
		
		if (carry > 0) {
            LargeNumber biggerResult = new LargeNumber(result.numberOfDigits() + 1);
			System.arraycopy(result.digits, 0, biggerResult.digits, 1, result.numberOfDigits());
			biggerResult.digits[0] = carry;
			return biggerResult;
		}
		return result;
	}
}
