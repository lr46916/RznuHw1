package hr.fer.rznu.json;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

public class JSONViewFromater {
	
	public static void format(Map<String, Object> model, HttpServletResponse response){
		
		ObjectMapper om = new ObjectMapper();
		
		JSONObject result = new JSONObject();
		
		for(String key : model.keySet()) {
			String jsonString = "";
			Object target = model.get(key);
			try {
				jsonString = om.writer().writeValueAsString(target);
			} catch (IOException e) {
				jsonString = "JSON not supported";
//				e.printStackTrace();
			}
			result.put(key, jsonString);
		}

		try {
			response.getWriter().write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}	
