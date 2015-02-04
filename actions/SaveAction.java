package actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import main.*;

@SuppressWarnings("serial")
public class SaveAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			FileOpen.fileSave(GUI.getTabbedPane());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//cry????
			e1.printStackTrace();
		}

	}

}
