package actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import main.FileOpen;

@SuppressWarnings("serial")
public class ExitAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//ask the user to save before closing 
		Object[] options = {"Save and Exit", "Exit Without Saving","Cancel"};

		int result = JOptionPane.showOptionDialog(null, "Do you want to save before you close?","Close Dialog",
		    JOptionPane.YES_NO_CANCEL_OPTION,  JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
		//find what the user clicked 
		if (result == JOptionPane.YES_OPTION)
		{
			try {
				//save the users file
				FileOpen.fileSaveBeta(main.GUI.tabbedPane);
				System.exit(0);
			} catch (IOException e1) {
				// TODO tell the user that the file didn't save :(
			} 
		
		} else if(result == JOptionPane.NO_OPTION)
		{
			System.exit(0);	
		} 
	}

}
