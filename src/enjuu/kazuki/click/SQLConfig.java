package enjuu.kazuki.click;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.Spring;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SQLConfig {
	
	public static Object config = null;
	public static JSONObject objconfig = null;
	
	public static void loadConfig() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
    	Object obj = null;
		obj = parser.parse(new FileReader("sqlconfig.json"));
		config = obj;
        JSONObject jsonObject = (JSONObject) obj;
        objconfig = jsonObject;
        System.out.println("[INFO]: Successfully loaded SQLConfig.json");
	}
	
	@SuppressWarnings({ "unchecked" })
	public static void createConfig()  {
		File f = new File("sqlconfig.json");
		if(f.exists()) {
			System.out.println("[INFO]: Found SQLConfig! No one must be created");
		}else{
			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("[WARNING]: No SQLConfig Found! Create one...");
			JSONObject obj = new JSONObject();
			obj.put("hostname", "localhost");
			obj.put("port", "3306");
			obj.put("database", "ripple");
			obj.put("username", "root");
			obj.put("password", "");
			try (FileWriter file = new FileWriter("sqlconfig.json")) {
				file.write(obj.toJSONString());
				System.out.println("[INFO]: Successfully created SQLConfig...");
			} catch (IOException e) {
				System.err.println("[ERROR]: Can't create SQLConfig...");
				e.printStackTrace();
			}
		}
    
	}
	
	public static String getString(String string) {
		try{
			String result = (String) objconfig.get(string);
			return result;
		}catch (Exception e){
			return "Error Load "+string+ " in sqlconfig.json! Is it possible that your String ins't a String?";
		}
	}
	
	public static int getInt(Spring path) {
		try{
			int result = (int) objconfig.get(path);
			return result;
		}catch (Exception e){
			System.out.println("I can't load the int! Maybe the in the SQLConfig the Number ins't a int. Try use a Long");
			return 404;
		}
	}
	
	public static Boolean getBool(String string) {
		try{
			Boolean result = (Boolean) objconfig.get(string);
			return result;
		}catch (Exception e){
			System.out.println("Error Loading the SQLConfig");
			return false;
		}
	}
	
	public static Long getLong(String string) {
		try{
			Long result = (Long) objconfig.get(string);
			return result;
		}catch (Exception e){
			System.out.println("Error Loading the SQLConfig");
			return null;
		}
	}

}