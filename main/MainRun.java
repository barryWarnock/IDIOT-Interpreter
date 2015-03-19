package main;

/**
 * 
 * @author bolster
 * This class creates two threads for the application and contains
 * the main method. 
 */
public class MainRun {
 
	private static Interpreter interpreter=null;
	
	public static void main(String[] args) {
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
}