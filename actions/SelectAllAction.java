package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextPane;

import main.GUI;
/**
 * 
 * 
 * This class selects all of the text in the open tab
 * @author bolster
 */
@SuppressWarnings("serial")
public class SelectAllAction extends AbstractAction {


	@Override
	public void actionPerformed(ActionEvent e) {
		
		JTextPane txt=null;
		try {
			txt = GUI.getFocusTextPane();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//select the text
	    txt.selectAll();
		
		
	}

}
