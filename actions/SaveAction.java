package actions;

import java.awt.event.ActionEvent;
import java.io.File;

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
		
		try {
			//find the path of the previous save file
			JEditorPane editor = GUI.getFocusEditorPane();
			int index = GUI.getTabbedPane().indexOfComponent(editor);
			String filePath = null;
			try {
				
				filePath = GUI.getFilePathList().get(index+2);
			} catch (ArrayIndexOutOfBoundsException e2){}//do nothing :(
			
			
			
			System.out.println(GUI.getFilePathList().toString());
			
			//if there is no previous save direct the user to SaveAs 
			if(filePath==null){
					
				SaveAsAction saveAs = new SaveAsAction();
				saveAs.actionPerformed(new ActionEvent(saveAs, 1, ""));
			        
			}else{
				
				//adds .IDIOT if it is not there. 
				if(!filePath.endsWith(".IDIOT"))
					filePath+=".IDIOT";
				File file = new File(filePath);
				FileUtils.writeStringToFile(file, editor.getText());	
				System.out.println("test5");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
	}
}
