package actions;
import main.*;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;

@SuppressWarnings("serial")
public class CompileAction extends AbstractAction {

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		

		Thread thread = new Thread() {
			public void run() {
				//sends a string to the interpreter
				try {
					JEditorPane editor = GUI.getFocusEditorPane();
					MainRun.getInterpreter().run(editor.getText());
				} catch (Exception e1) {
					//TODO error message pop-up
					e1.printStackTrace();
				}
			}
		};
		thread.start();
	}

}
