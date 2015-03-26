package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextPane;

import main.GUI;
/**
 * 
 * @author bolster
 * This action removes the selected text and copies it to the clipboard.
 */
@SuppressWarnings("serial")
public class CutAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			JTextPane txt = GUI.getFocusTextPane();
			txt.cut();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
