package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextPane;

import main.GUI;

@SuppressWarnings("serial")
public class CopyAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			JTextPane txt = GUI.getFocusTextPane();
			txt.copy();
		} catch (Exception e1) {
			//cry??
		}
		
	}

}
