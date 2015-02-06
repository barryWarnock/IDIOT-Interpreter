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
		
		JEditorPane editor = GUI.getFocusEditorPane();
		//select the text
	    editor.selectAll();
		
		
	}

}
