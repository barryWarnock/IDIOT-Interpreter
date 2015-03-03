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
		
		HintRun.hintthing();
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				interpreter = new Interpreter();
			}
		});
		
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				GUI main=new GUI();
        		main.createFrame("IDIOT IDE", 500, 500);
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