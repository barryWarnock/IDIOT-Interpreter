package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;

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
		
		String fileName=null;

		//choose a filename to save the file under. 
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
			fileName= fileChooser.getSelectedFile().toString();
				
			//adds .IDIOT extension to the file if it is not there. 
			if(!fileName.endsWith(".IDIOT"))
					fileName+=".IDIOT"; 
			File file = new File(fileName);
				  
			//saves the current file open in the JPane.  	  
			JTextPane textPane = null;
			try {
				textPane = GUI.getFocusTextPane();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String txt = textPane.getText();
			Thread thread = new Thread() {
				public void run(){
					try {
						FileUtils.writeStringToFile(file, txt,"UTF-8");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			thread.start();
			GUI.getFilePathList().set(e.getID(), fileName);
		}
	}
}
