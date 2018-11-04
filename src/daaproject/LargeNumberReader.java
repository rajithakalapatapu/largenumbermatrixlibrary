package daaproject;

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

	public List<LargeNumber> readFromFile(String filepath) {
		File file = new File(filepath);

		List<LargeNumber> multiplicands = new ArrayList<>();

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
				multiplicands.add(new LargeNumber(st));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return multiplicands;
	}
}
