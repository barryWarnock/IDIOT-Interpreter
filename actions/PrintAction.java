package actions;
import main.GUI;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;

@SuppressWarnings("serial")
public class PrintAction extends AbstractAction {

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		
		try {
			JEditorPane editor = GUI.getFocusEditorPane();
			editor.print();	
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
	}
}
