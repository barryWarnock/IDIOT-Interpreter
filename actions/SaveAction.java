package actions;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;

import main.*;

/**TODO this should NOT ask where to save if the file already has a previous
 * save
 * TODO move parts of file open to this class 
 */
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
