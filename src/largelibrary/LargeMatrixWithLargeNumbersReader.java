package largelibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LargeMatrixWithLargeNumbersReader {

	public LargeMatrixWithLargeNumbersReader() {

	}

	public LargeMatrixWithLargeNumbers parseFileContents(String filePath) {
		LargeMatrixWithLargeNumbers result = null;
		File file = new File(filePath);

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String st;
		try {
			/* first line is dimensions */
			st = br.readLine();
			int dimensions = Integer.valueOf(st);
			result = new LargeMatrixWithLargeNumbers(dimensions);

			/* loop till complete */
			while ((st = br.readLine()) != null) {
				st = st.substring(1, st.length() - 1);
				String[] tokens = st.split(",");
				result.addElement(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), tokens[2]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

}
