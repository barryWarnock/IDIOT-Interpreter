package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.apache.commons.io.FileUtils;

import main.*;
/**
 * This Action saves the current open file. It performs the necessary cleanup
 * such as renaming the tab to the most recent name as well.
 */
@SuppressWarnings("serial")
public class SaveAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
	
		JTextPane textPane = null;
		try {
			textPane = GUI.getFocusTextPane();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
			
		//find the path of the previous save file
		JScrollPane scroll = (JScrollPane) GUI.getTabbedPane()
				.getComponentAt(GUI.getTabbedPane().getSelectedIndex());
		
		int index = GUI.getTabbedPane().indexOfComponent(scroll);
		String filePath = null;
		try {			
			filePath = GUI.getFilePathList().get(index);
		} catch (ArrayIndexOutOfBoundsException e2){
			e2.printStackTrace();
		}
			
		//if there is no previous save direct the user to SaveAs 
		if(filePath==null){		
			SaveAsAction saveAs = new SaveAsAction();
			saveAs.actionPerformed(new ActionEvent(saveAs, index, ""));        
		}else{
			//adds .IDIOT to the file name. 
			if(!filePath.endsWith(".IDIOT"))
				filePath+=".IDIOT";
			File file = new File(filePath);
			String txt = textPane.getText();
	
			//Change the name of the current tab to the file name
			JPanel pane = (JPanel) GUI.getTabbedPane().getTabComponentAt(index);
			JLabel label = (JLabel) pane.getComponent(0);
			label.setText(filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf(".IDIOT")));
			
			Thread thread = new Thread() {
				public void run(){
					try {
						//Does the saving
						FileUtils.writeStringToFile(file, txt,"UTF-8");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();	
		}
	}
}
