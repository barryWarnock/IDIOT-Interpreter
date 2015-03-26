package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextPane;

import main.GUI;
/**
 * 
 * @author bolster
 * This Action copies the selected text to the clipboard.
 */
@SuppressWarnings("serial")
public class CopyAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			JTextPane txt = GUI.getFocusTextPane();
			txt.copy();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

}
