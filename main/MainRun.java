package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * This class contains the main method for the entire application. It handles 
 * the loading and sharing of the preferences file as a Properties object.
 * @version 31.415926
 * @author bolster
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
			//try to load recent files 
			for(int i=0; 10>i; i=i+2)
			{
				String str = preferences.getProperty("JMenuItem"+i);
				if(str!=null){
					String path = str.substring(0, str.indexOf("*.*.*.*.*"));
					String name = str.substring(str.indexOf("*.*.*.*.*")+9,str.length());
					GUI.addRecentFile(path, name);
				}
			}
		}catch(IOException e)
		{
			//if there is no preferences to load then it will be blank.
		}
		
		//initialize the frames and back-end
		interpreter = new Interpreter();
		new GUI("IDIOT IDE", 500, 500);
		new HelpfulHints(true);

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
	 * Saves the preferences to a file on the disk	
	 */
	public static void saveProperty()
	{
		//store the most recent file locations
		String[] str = GUI.dumpRecentFileMenuItems();
		for(Integer i=0; str.length>i;i=i+2)
		{
			preferences.setProperty("JMenuItem"+i, str[i]+"*.*.*.*.*"+str[i+1]);
		}

		//try saving the preferences file
		FileOutputStream output;
		try {
			output = new FileOutputStream("preferences.dat");
			preferences.store(output, "IDIOT Interpreter Preferences");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}