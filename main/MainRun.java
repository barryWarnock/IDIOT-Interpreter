package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author bolster
 * This class creates two threads for the application and contains
 * the main method. 
 */
public class MainRun {
 
	private static Interpreter interpreter=null;
	
	private static Properties preferences = new Properties();
	
	public static void main(String[] args) {
		
		//see if there is a preferences file to load
		try
		{
			FileInputStream input = new FileInputStream("preferences.dat");
			preferences.load(input);
			input.close();
		}catch(IOException e)
		{
			
		}
		
		interpreter = new Interpreter();
		new GUI("IDIOT IDE", 500, 500);
		new HelpfulHints();

	}
	
	/**
	 * @return Interpreter that is in its own thread so 
	 * that it may be called by whatever needs it
	 */
	public static Interpreter getInterpreter()
	{
		return interpreter;
	}
	
	/**
	 * 
	 * @param key is a string name of the preference to search for.
	 * @return The property value of the preference.
	 */
	public static Boolean getPreferences(String key)
	{
		if("true".equals(preferences.getProperty(key)))
			return true;
		return false;
	}
	
	/**
	 * @param key is a string name of the preference to set.
	 * @param value is the value of the preference.
	 */
	public static void setPreferences(String key, Boolean value)
	{
		preferences.setProperty(key, value.toString());
	}
	
	/**
	 * DO NOT CALL THIS UNLESS YOU KNOW WHAT YOU'RE DOING!!!!
	 * 
	 * @return The preferences as a property object.
	 */
	public static Properties getProperty()
	{
		return preferences;
	}
	
	
}









