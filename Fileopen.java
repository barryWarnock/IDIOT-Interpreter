/** This opens the file through the file manager
 * 
 */

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFileChooser;

import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Fileopen {
   

	public class fileManager {

	}

	public static File fileManager() {
   	 JFileChooser fileChooser = new JFileChooser();
   	 fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
int result= fileChooser.showOpenDialog(fileChooser);

//I can add a filter into this to get only a certain resut

if(result==JFileChooser.APPROVE_OPTION){
    //user selects file
    File selectedFile = fileChooser.getSelectedFile();
    return selectedFile; 
}
else{
return null;
}



   	 
    }

    
   	 
   }



    
    
    
    
    


