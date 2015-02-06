package main;

public class MainRun {

	private static Interpreter interpreter=null;
	
	public static void main(String[] args) {
		
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
	
	public static Interpreter getInterpreter()
	{
		return interpreter;
	}
}