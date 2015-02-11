package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;

import main.GUI;

@SuppressWarnings("serial")
public class CutAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			JEditorPane editor = GUI.getFocusEditorPane();
			editor.cut();
		} catch (Exception e1) {
			//cry??
		}

	}

}
