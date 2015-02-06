package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;

import org.apache.commons.io.FileUtils;

import main.*;

/**TODO this should NOT ask where to save if the file already has a previous
 * save
 * TODO find a way to store the directory location of the previous save
 * TODO find a way to get filename from the editor pane or something.
 */
@SuppressWarnings("serial")
public class SaveAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String FILENAME=null;
		
		try {
			JEditorPane editor = GUI.getFocusEditorPane();
			
			//if there is no previous save direct the user to SaveAs 
			if(FILENAME==null){
					
				SaveAsAction saveAs = new SaveAsAction();
				saveAs.actionPerformed(new ActionEvent(saveAs, 1, ""));
			        
			}else{
				//adds .IDIOT if it is not there. 
				File file = new File(FILENAME); 
				if(!FILENAME.endsWith(".IDIOT"))
				FILENAME+=".IDIOT";
				FileUtils.writeStringToFile(file, editor.getText());
					  
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//cry????
			e1.printStackTrace();
		}

	}

}
