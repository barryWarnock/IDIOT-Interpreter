package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import org.apache.commons.io.FileUtils;
import main.GUI;

/**
 * @author bolster
 * The class responsible for saving files. It takes the file from the 
 * current JEditorPane. 
 */
@SuppressWarnings("serial")
public class SaveAsAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e){
		
		String FILENAME=null;
		
			JEditorPane editor = GUI.getFocusEditorPane();
			
			//choose a filename
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
				FILENAME= fileChooser.getSelectedFile().toString();
				
				//adds .IDIOT if it is not there. 
				if(!FILENAME.endsWith(".IDIOT"))
					FILENAME+=".IDIOT"; 
				File file = new File(FILENAME);
			  
			  //save the open file 
			  try {
				FileUtils.writeStringToFile(file, editor.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//cry
				e1.printStackTrace();
			}	
			}
		
	}
}
