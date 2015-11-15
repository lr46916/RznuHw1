package hr.fer.rznu.logging;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class RznuLogger implements ILogger {
	private BufferedWriter writter;

	public void setOutput(String pathToFile) {
		if (writter != null) {
			try {
				writter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			writter = new BufferedWriter(
					new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(new File(pathToFile), true))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeLog(String input) {
		try {
			writter.write(input);
			writter.newLine();
			writter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			writter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
