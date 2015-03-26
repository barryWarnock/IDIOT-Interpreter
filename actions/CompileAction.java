package actions;
import main.GUI;
import main.MainRun;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/**
 * 
 * @author bolster
 * This Action sends the current file to the interpreter for processing.
 */
@SuppressWarnings("serial")
public class CompileAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Thread thread = new Thread() {
			public void run() {
				//sends a string to the interpreter for processing 
				try {
					MainRun.getInterpreter().run(GUI.getFocusTextPane().getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};
		thread.start();
	}
}
