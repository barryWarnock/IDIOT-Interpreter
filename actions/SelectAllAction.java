package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;

import main.GUI;
/**
 * 
 * @author bolster
 * This class selects all of the text in the open tab
 * 
 */
@SuppressWarnings("serial")
public class SelectAllAction extends AbstractAction {


	@Override
	public void actionPerformed(ActionEvent e) {
		
		JEditorPane editor=null;
		try {
			editor = GUI.getFocusEditorPane();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//select the text
	    editor.selectAll();
		
		
	}

}
