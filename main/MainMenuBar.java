package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
/**
 * @author bolster
 * @version 0.1.0
 * Creates and returns a populated menu bar for the IDE.
 * <p>
 * Each menu (file, edit and, help) are created separately. 
 * They are added to the menubar before their components are made. 
 * The menuItems are made in the order of: Name, Accelerator (if applicable), Accessible Description, action command,
 * action listener. The action listener method implemented looks for the unique action command of each button before
 * performing the appropriate task. 
 * <p>
 * There is no hashCode or equals methods for this class as there are no variables.
 * 
 */
@SuppressWarnings({"serial"})
public class MainMenuBar extends JMenuBar implements ActionListener
{



	MainMenuBar() {
		
        JMenu file, edit, help;
        JMenuItem menuItem;
 
 
        //Build the file menu.
        file = new JMenu("File");
        file.getAccessibleContext().setAccessibleDescription("Perform operations to do with files");
        add(file);
        
        //menu items for file (6)
        menuItem = new JMenuItem("New");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Creates a new file");
        menuItem.setActionCommand("New");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
        menuItem = new JMenuItem("Open");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Opens a file from the disk");
        menuItem.setActionCommand("Open");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
        menuItem = new JMenuItem("Save");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Saves your current file");
        menuItem.setActionCommand("Save");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
        menuItem = new JMenuItem("Save As");
        //menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Saves your current file to any directory");
        menuItem.setActionCommand("Save As");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
        file.addSeparator(); // nice line separating different things
        
        menuItem = new JMenuItem("Print");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Print the current file");
        menuItem.setActionCommand("Print");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
        menuItem = new JMenuItem("Exit");
        menuItem.getAccessibleContext().setAccessibleDescription("Closes the program");
        menuItem.setActionCommand("Exit");
        menuItem.addActionListener(this);
        file.add(menuItem);       

        //The edit bar 
        edit = new JMenu("Edit");
        edit.getAccessibleContext().setAccessibleDescription("Perform operations on your current file");
        add(edit);
        
        //the menu items for edit(4)
        menuItem = new JMenuItem("Cut");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Removes selected text and copies it to the clipboard");
        menuItem.setActionCommand("Cut");
        menuItem.addActionListener(this);
        edit.add(menuItem);
        
        menuItem = new JMenuItem("Copy");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Copies selsected text to the clipboard");
        menuItem.setActionCommand("Copy");
        menuItem.addActionListener(this);
        edit.add(menuItem);
        
        menuItem = new JMenuItem("Paste");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
        		"Puts the content of the clipboard to the right of the cursor");
        menuItem.setActionCommand("Paste");
        menuItem.addActionListener(this);
        edit.add(menuItem);
        
        menuItem = new JMenuItem("Select All");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Highlights all text from the current file");
        menuItem.setActionCommand("Select All");
        menuItem.addActionListener(this);
        edit.add(menuItem);
 
        //The help bar
        help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_N);
        help.getAccessibleContext().setAccessibleDescription("Find help for using the current program");
        add(help);
        
        //menu items for help(2)
        menuItem = new JMenuItem("View Help");
        menuItem.getAccessibleContext().setAccessibleDescription("Opens a help window");
        menuItem.setActionCommand("View Help");
        menuItem.addActionListener(this);
        help.add(menuItem);
        
        menuItem = new JMenuItem("About My IDE");
        menuItem.getAccessibleContext().setAccessibleDescription("Priveds information about the IDE");
        menuItem.setActionCommand("About My IDE");
        menuItem.addActionListener(this);
        help.add(menuItem);

    }
	

	@Override
	public void actionPerformed(ActionEvent e) {

		String action = e.getActionCommand();
	 
	    // See what menuItem was clicked and do the appropriate thing 
		//TODO find the appropriate thing to do, currently just prints a string
		if ("Copy".equals(action)) { 
	      	System.out.println("Copy");
	      	
		} else if ("Cut".equals(action)) 
		{ 
			System.out.println("Cut");
			
		} else if ("Paste".equals(action)) 
		{ 
			System.out.println("Paste");
			
		} else if ("New".equals(action)) 
		{ 
			System.out.println("New");
			
		} else if ("Open".equals(action)) 
		{ 
			System.out.println("Open");
			
		} else if ("Print".equals(action)) 
		{ 
			System.out.println("Print");
			
		} else if ("Save".equals(action)) 
		{ 
			System.out.println("Save");
			
		} else if ("Save As".equals(action)) 
		{ 
			System.out.println("Save As");
			
		} else if ("Exit".equals(action)) 
		{ 
			
			Object[] options = {"Save and Exit", "Exit Without Saving","Cancel"};
	
			int result = JOptionPane.showOptionDialog(null, "Do you want to save before you close?","Close Dialog",
			    JOptionPane.YES_NO_CANCEL_OPTION,  JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
			//find what the user clicked 
			if (result == JOptionPane.YES_OPTION)
			{
				//TODO make this save the file then exit
				//System.exit(0);
			
			} else if(result == JOptionPane.NO_OPTION)
			{
				System.exit(0);	
			} 
		} else if ("Select All".equals(action)) 
		{ 
			System.out.println("Select All");
			
		} else if ("View Help".equals(action)) 
		{ 
			System.out.println("View Help");
			
		} else if ("About My IDE".equals(action)) 
		{ 
			//try to find a nice icon for the dialog box but don't display it if it can't be found
			ImageIcon icon=null;
			URL imageURL = this.getClass().getResource("/images/infoIcon.png");
			if (imageURL != null) {                   
				icon=new ImageIcon(imageURL,"Info");
	        } 
			
			JOptionPane.showMessageDialog(null, "The IDIOT editor was made by:\n\n" 
					+ "Jeremy Bolster,\n"
					+ "Travis Kurucz,\n"
					+ "Timothy Hillier,\n"
					+ "Dean Kution,\n"
					+ "Barry Warnock\n\n"
					+ "Thanks to Visual Pharm for the icons which were released under\n"
					+ "Creative Commons Attribution-No Derivative Works 3.0 Unported\n"
					+ "They can be viewed at https://www.iconfinder.com/icons/27860/",
					"About the IDIOT editor",JOptionPane.PLAIN_MESSAGE,icon);	
		} 
	}

	//TODO make this into a list of the menu items
	@Override
	public String toString() {
		return "This is a menubar for the application";
	}
	
	
}
