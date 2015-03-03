package actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

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
			JEditorPane editor = GUI.getFocusEditorPane();
			
			//find the path of the previous save file
			JScrollPane scroll = (JScrollPane) GUI.getTabbedPane().getComponentAt(GUI.getTabbedPane().getSelectedIndex());
			int index = GUI.getTabbedPane().indexOfComponent(scroll);
			String filePath = null;
			try {
				
				filePath = GUI.getFilePathList().get(index);
			} catch (ArrayIndexOutOfBoundsException e2){}//do nothing :(
			
			//if there is no previous save direct the user to SaveAs 
			if(filePath==null){
					
				SaveAsAction saveAs = new SaveAsAction();
				saveAs.actionPerformed(new ActionEvent(saveAs, index, ""));
			        
			}else{
				
				//adds .IDIOT if it is not there. 
				if(!filePath.endsWith(".IDIOT"))
					filePath+=".IDIOT";
				File file = new File(filePath);
				FileUtils.writeStringToFile(file, editor.getText(),"UTF-8");
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
	}
}
