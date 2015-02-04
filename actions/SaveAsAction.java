package actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import main.FileOpen;
import main.GUI;

/**
 * TODO move parts of file open to this class 
 * @author bolster
 *
 */
@SuppressWarnings("serial")
public class SaveAsAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			FileOpen.fileSaveAs(GUI.getTabbedPane());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//cry????
			e1.printStackTrace();
		}
	}
}
