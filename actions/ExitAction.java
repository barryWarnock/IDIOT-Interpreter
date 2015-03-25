package actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import main.MainRun;


@SuppressWarnings("serial")
public class ExitAction extends AbstractAction {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//This next bit attempts to save the preferences to a file
		MainRun.saveProperty();
		
		//ask the user to save before closing 
		Object[] options = {"Save and Exit", "Exit Without Saving","Cancel"};

		int result = JOptionPane.showOptionDialog(null, "Do you want to save before you close?","Close Dialog",
		JOptionPane.YES_NO_CANCEL_OPTION,  JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
		
		//find what the user clicked 
		if (result == JOptionPane.YES_OPTION)
		{
			//call the save action
			SaveAction save = new SaveAction();
			save.actionPerformed(new ActionEvent(save, 1, ""));
			System.exit(0);
		
		} else if(result == JOptionPane.NO_OPTION)
		{
			System.exit(0);	
		} 
	}
}
