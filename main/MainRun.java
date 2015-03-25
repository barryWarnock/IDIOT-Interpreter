package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author bolster
 * This class contains the main method for the entire application. It handles 
 * the loading and sharing of the preferences file as a Properties object.
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
			//if there is no preferences to load then it will be blank.
		}
		
		//initialize the frames and back-end
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
	public static void saveProperty()
	{
		FileOutputStream output;
		try {
			output = new FileOutputStream("preferences.dat");
			preferences.store(output, "IDIOT Interpreter Preferences");
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}