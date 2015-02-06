package actions;
import main.GUI;

import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;

@SuppressWarnings("serial")
public class PrintAction extends AbstractAction {

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JEditorPane editor = GUI.getFocusEditorPane();
		
		try {
			editor.print();	
		} catch (PrinterException e1) {
			// TODO Auto-generated catch block
		}
	}
}
