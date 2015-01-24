package main;
/** This opens the file through the file manager
 * 
 */

import javax.swing.JFileChooser;

import java.io.File;

public class FileOpen {

	public static File fileManager() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(fileChooser);

		// I can add a filter into this to get only a certain result

		if (result == JFileChooser.APPROVE_OPTION) { 
			// user selects file,return the selected file to whatever called this method
			File selectedFile = fileChooser.getSelectedFile();
			return selectedFile;
		} else {
			return null;
		}
	}

}
