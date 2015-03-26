package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextPane;

import main.GUI;
/**
 * 
 * @author bolster
 * This Action pastes the contents of the clipboard to the text at the selected index. 
 */
@SuppressWarnings("serial")
public class PasteAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			JTextPane txt = GUI.getFocusTextPane();
			txt.paste();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
