package main;

import javax.swing.SwingUtilities;

/**
 * 
 * @author bolster
 * This class creates two threads for the application and contains
 * the main method. 
 */
public class MainRun {
 
	private static Interpreter interpreter=null;
	
	public static void main(String[] args) {
		
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				interpreter = new Interpreter();
			}
		});
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new GUI("IDIOT IDE", 500, 500);
			}
		});
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new HelpfulHints();
			}
		});
		
		
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