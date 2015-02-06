package main;

import java.io.File;
import javax.swing.JFileChooser;

/** This opens the file through the file manager
 * saves files differently depending on which save was used
 * appends .IDIOT to the end of files
 * 1-28-15
 * TODO merge this class into the action classes and eliminate this class
 */
public class FileOpen {
	
	// Stores the name of the file for use of the save function
	static String FILENAME;

	public static File fileManager() throws Exception
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int option = fileChooser.showOpenDialog(fileChooser);

		// I can add a filter into this to get only a certain result

		if (option == JFileChooser.APPROVE_OPTION) {
			// user selects file,return the selected file to whatever called
			File selectedFile = fileChooser.getSelectedFile();

			return selectedFile;
			
		} else 
		{
			final  Exception e = new Exception();
			throw e;
		}
	}
}