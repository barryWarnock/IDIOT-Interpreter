package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.apache.commons.io.FileUtils;

import main.GUI;

/**
 * The class responsible for saving files. It takes the file from the 
 * current JEditorPane. 
 */
@SuppressWarnings("serial")
public class SaveAsAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e){
		
		String filePath=null;

		//choose a filename to save the file under. 
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
			filePath= fileChooser.getSelectedFile().toString();
				
			//adds .IDIOT extension to the file if it is not there. 
			if(!filePath.endsWith(".IDIOT"))
					filePath+=".IDIOT"; 
			File file = new File(filePath);
				  
			//saves the current file open in the JPane.  	  
			JTextPane textPane = null;
			try {
				textPane = GUI.getFocusTextPane();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String txt = textPane.getText();
			
			//Change the name of the current tab
			JScrollPane scroll = (JScrollPane) GUI.getTabbedPane().getComponentAt(GUI.getTabbedPane().getSelectedIndex());
			int index = GUI.getTabbedPane().indexOfComponent(scroll);
			JPanel pane = (JPanel) GUI.getTabbedPane().getTabComponentAt(index);
			JLabel label = (JLabel) pane.getComponent(0);
			label.setText(filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf(".IDIOT")));
			
			Thread thread = new Thread() {
				public void run(){
					try {
						FileUtils.writeStringToFile(file, txt,"UTF-8");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
			GUI.getFilePathList().set(e.getID(), filePath);
		}
	}
}
