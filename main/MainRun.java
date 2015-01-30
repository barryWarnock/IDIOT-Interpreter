package main;

import javax.swing.JTextArea;



public class MainRun {

	private static Interpreter interpreter=null;
	
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				GUI main=new GUI();
        		main.createFrame("IDIOT IDE", 500, 500);
			}
		});
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				//TODO make the interpreter's constructor not need a textarea
				//make it build without it but fail to interpret without one being added
				interpreter = new Interpreter(new JTextArea());
			}
		});
	}
	
	public static Interpreter getInterpreter()
	{
		return interpreter;
	}
}