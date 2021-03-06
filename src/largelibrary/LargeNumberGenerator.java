package largelibrary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class LargeNumberGenerator {

	public LargeNumberGenerator() {
	}

	public void writeToFile(String filePath, int digits, int numbers) {
		File file = new File(filePath);
		FileWriter fr = null;
		try {
			fr = new FileWriter(file);
			for (int i = 0; i < numbers; i++) {
				fr.write(generateLargeNumber(digits));
				fr.write("\n");
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

	private String generateLargeNumber(int digits) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < digits; i++) {
			sb.append(random.nextInt(10));
		}
		String generatedNumber = sb.toString();
		return sb.toString();
	}

}
