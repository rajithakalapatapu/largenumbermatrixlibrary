package largelibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LargeNumberReader {

	public LargeNumberReader() {
	}

	public List<String> readFromFile(String filepath) throws FileNotFoundException {
		File file = new File(filepath);
		if (!file.exists()) {
			throw new FileNotFoundException(filepath + " not found");
		}

		List<String> multiplicands = new ArrayList<>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String st;
		try {
			while ((st = br.readLine()) != null) {
				multiplicands.add(st);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return multiplicands;
	}
}
