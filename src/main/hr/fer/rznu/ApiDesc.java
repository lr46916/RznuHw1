package hr.fer.rznu;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

public class ApiDesc {

	private String action;
	private String method;
	private String inputs;
	private String format;
	private String desc;

	public ApiDesc() {
	}

	public ApiDesc(String action, String method, String inputs, String format, String desc) {
		super();
		this.action = action;
		this.method = method;
		this.inputs = inputs;
		this.format = format;
		this.desc = desc;
	}

	public String getAction() {
		return action;
	}

	public String getMethod() {
		return method;
	}

	public String getInputs() {
		return inputs;
	}

	public String getFormat() {
		return format;
	}

	public String getDesc() {
		return desc;
	}

	public static List<ApiDesc> getApis() {
		return apis;
	}

	public String toString() {
		return action + ", " + method + ", " + inputs + ", " + format + ", " + desc;
	}

	private static List<ApiDesc> apis;

	public static List<ApiDesc> getApiList() {
		return apis;
	}

	public static void setFilename(String fn) throws IOException {
		apis = loadApiDescription(fn);
	}

	private static List<ApiDesc> loadApiDescription(String pathToFile) throws IOException {

		ClassPathResource classPathRes = new ClassPathResource(pathToFile);

		BufferedReader br = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(classPathRes.getInputStream())));
		String line = null;

		List<ApiDesc> lst = new ArrayList<>();

		while ((line = br.readLine()) != null) {

			String[] data = line.split("[:][:][:]");

			ApiDesc api = new ApiDesc(data[0], data[1], data[2], data[3], data[4]);

			lst.add(api);

		}
		br.close();
		return lst;
	}

}
