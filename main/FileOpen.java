package main;

/** This opens the file through the file manager
 * 1-26-15
 */

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.apache.commons.io.FileUtils;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class FileOpen {
	
	// Stores the name of the file for use of the save function
	static String fileName;
	//static JEditorPane paneName = GUI.getPaneName();

	public static File fileManager() throws Exception
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System
				.getProperty("user.home")));
		int option = fileChooser.showOpenDialog(fileChooser);

		// I can add a filter into this to get only a certain result

		if (option == JFileChooser.APPROVE_OPTION) {
			// user selects file,return the selected file to whatever called
			// this method
			File selectedFile = fileChooser.getSelectedFile();
			fileName = (selectedFile.getName());
			System.out.println(fileName);
			// paneName = fileName;
			return selectedFile;
		} else {
			final  Exception e = new Exception();
			throw e;
		}

	}

	// This probably wont work.
	public static File fileSave() throws IOException {
		FileWriter text = new FileWriter(fileName);
		System.out.println("file " + fileName);
		System.out.println("pane " + paneName);
		text.write(paneName.getText());
		text.close();
		return null;

	}

	// This has a better chance of working
	// I think...
	public static void fileSaveOmega() {
		FileWriter out;
		try {
			out = new FileWriter(fileName);
			// paneName is wrong i think
			// fuck

			out.write(paneName.getText());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Works as a file saving method. Just send the tabbedPane into it. 
	 * @param tabbedPane just the tabbedPane from the GUI
	 * @throws IOException if the file can't be saved for some reason 
	 */
	public static void fileSaveAlpha(JTabbedPane tabbedPane) throws IOException
	{
		//returns the tab component with focus
		JScrollPane scroll = (JScrollPane) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		if(scroll==null){
		}else{
			JEditorPane editor = (JEditorPane) scroll.getComponent(0).getComponentAt(100, 100);
			if(editor==null){
			}else{
				//TODO append the file name with .IDIOT
				//choose a filename
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile();
				  //save the open file 
				  FileUtils.writeStringToFile(file, editor.getText());			
				}
			}
		}
	}
}
