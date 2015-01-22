package main;

public class MainRun {

	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				GUI main=new GUI();
        		main.createFrame("IDIOT IDE", 500, 500);
			}
		});
	}
}