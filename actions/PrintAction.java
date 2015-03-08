package actions;
import main.GUI;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class PrintAction extends AbstractAction {

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		
		try {
			JTextPane txt = GUI.getFocusTextPane();
			txt.print();	
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
	}
}
