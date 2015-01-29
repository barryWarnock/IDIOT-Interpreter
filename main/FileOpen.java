package main;

/** This opens the file through the file manager
 * saves files differently depending on which save was used
 * appends .IDIOT to the end of files
 * 1-28-15
 */

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.apache.commons.io.FileUtils;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

public class FileOpen {
	
	// Stores the name of the file for use of the save function
	static String FILENAME;
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
			File selectedFile = fileChooser.getSelectedFile();
			FILENAME = (selectedFile.toString());
			
			
			

			
			return selectedFile;
		} else {
			final  Exception e = new Exception();
			throw e;
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
				//choose a filename
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
					FILENAME= fileChooser.getSelectedFile().toString(); 
					//adds .IDIOT if it is not there. 
						if(!FILENAME.endsWith(".IDIOT"))
							FILENAME+=".IDIOT"; 
				  File file = new File( FILENAME);
				  //save the open file 
				  FileUtils.writeStringToFile(file, editor.getText());			
				}
			}
		}
	}
	
	
	public static void fileSaveBeta(JTabbedPane tabbedPane) throws IOException{
		//Automatically saves the file where it was, doesn't open the file chooser window.
		JScrollPane scroll = (JScrollPane) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		if(scroll==null){
		}else{
			JEditorPane editor = (JEditorPane) scroll.getComponent(0).getComponentAt(100, 100);
			if(editor==null){
			}else{
			}if(FILENAME==null){
				FileOpen.fileSaveAlpha(tabbedPane); 
			}else{
				//adds .IDIOT if it is not there. 
				File file = new File(FILENAME); 
				if(!FILENAME.endsWith(".IDIOT"))
					FILENAME+=".IDIOT";
				  FileUtils.writeStringToFile(file, editor.getText());
				  
				}
			}
		}
		
	}
	

