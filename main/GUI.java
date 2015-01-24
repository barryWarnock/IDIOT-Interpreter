package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * @author bolster
 * @version 0.0.1
 * This creates a GUI complete with a menubar and a toolbar
 * <p>
 * There is no hashCode or equals methods for this class as there are no static variables or objects.
 * As well there is no constructor because this class does not need to initialize anything.
 */
public class GUI implements ActionListener
{
	//this allows actionListeners to call tabbedPane.makeNewTab();
	private static JTabbedPane tabbedPane = new JTabbedPane();
	
	/**
	 * This method creates a populated frame 
	 * @param name is the name of the window that you wish to create
	 * @param x is the minimum width of the window
	 * @param y is the minimum height of the window
	 */
	public void createFrame(String name, int width, int height)
	{
        JFrame frame = new JFrame(name);
        
        //initialize the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(width, height));
        frame.setSize(2*width,2*height);
        frame.setLocationRelativeTo(null);

        //Open a tab with a blank window
        makeNewTab();
        
       //Create and set up the content pane (Menu and tabs and output and textfield)
        GUI content = new GUI();
        frame.setJMenuBar(makeMenuBar());
        frame.setContentPane(content.createPanel(height));
        frame.setVisible(true);
        
	}
	
	/**
	 * This creates a nice panel for everything to sit nicely on 
	 * 
	 * @param dividerLocation
	 * @return JPanel filled with toys for all the good girls and boys 
	 */
    private JPanel createPanel(int dividerLocation) {
    	
    	Dimension minimumSize=new Dimension(100,100);
    	
    	//create the panel that will be returned 
    	JPanel finalPanel=new JPanel(new BorderLayout());
    	
        tabbedPane.setMinimumSize(minimumSize);
        
		//create the terminal text area and give it a label 
        JTextArea output=new JTextArea();
        output.setMinimumSize(minimumSize);
        JPanel terminalPanel=new JPanel(new BorderLayout());
        terminalPanel.add(output);
        output.setBorder(BorderFactory.createTitledBorder(null, "console"));
        

        //Create the split pane 
    	JSplitPane contentPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    	contentPane.setOneTouchExpandable(true);
        contentPane.setOpaque(true);
        
        //add components to the split pane
        contentPane.add(tabbedPane);
        contentPane.add(terminalPanel);
        contentPane.setDividerLocation(dividerLocation);
        
        //add the split pane and toolbar to the panel
        finalPanel.add(makeToolbar(), BorderLayout.NORTH);
        finalPanel.add(contentPane, BorderLayout.CENTER);
        return finalPanel;
    }
	

	/**
	 * TODO this should have a close button on the edge of the tab
	 * @param file a file that you would like to open in a JEditorPane
	 * @throws BadLocationException, FileNotFoundException
	 */
	public void openNewTab(File file) throws BadLocationException, FileNotFoundException {
		
		//Create a scrolled text area to type into
		JEditorPane IDIOT_file_content = new JEditorPane();
		IDIOT_file_content.setEditable(true);
			
		// scans file into the JEditPane
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			Document doc = IDIOT_file_content.getDocument();
			//TODO This may require \r\n for windows, this should be tested
			doc.insertString(doc.getLength(), line+"\n", null);
		}
		scan.close();

		JScrollPane scroll = new JScrollPane(IDIOT_file_content);
		JPanel panel = new JPanel();     
		panel.setLayout(new BorderLayout());
		panel.add(scroll);
		tabbedPane.add(file.getName(),panel);	
		}
	
	/**
	 * TODO this should have a close button on the edge of the tab
	 */
	public void makeNewTab() 
	{
		
		JPanel panel = new JPanel();
		
		//Create a scrolled text area to type into
		JEditorPane IDIOT_file_content = new JEditorPane();
		IDIOT_file_content.setEditable(true);
		
		JScrollPane scroll = new JScrollPane(IDIOT_file_content);
		
		panel.setLayout(new BorderLayout());
		panel.add(scroll);
		tabbedPane.add("new file",panel);
	}
	
	/**
	 * 
	 * @return JToolBar full of different widgets 
	 */
	private JToolBar makeToolbar()
	{
		JToolBar toolbar=new JToolBar(JToolBar.HORIZONTAL);
		toolbar.setFloatable(true);
		
		//add buttons 
		toolbar.add(makeButton("saveIcon", "Save"));
		toolbar.add(makeButton("openIcon", "Open"));
		toolbar.add(makeButton("newIcon", "New"));
        toolbar.add(makeButton("copyIcon", "Copy"));
        toolbar.add(makeButton("cutIcon", "Cut"));
        toolbar.add(makeButton("pasteIcon", "Paste"));
        toolbar.add(makeButton("printIcon", "Print"));
        toolbar.add(makeButton("compileIcon", "Compile"));
        
        return toolbar;
	}
	
	/**
	 * 
	 * @param imageName name of the button's icon file 
	 * @param toolTipText This is not only the tooltip text but also the actionCommand of the button
	 * @return JButton A nice button 
	 */
	private JButton makeButton(String imageName, String toolTipText)
    {
    	//Search for the icon
        String imgLocation = "/images/"+ imageName + ".png";
        URL imageURL = this.getClass().getResource(imgLocation);

        //Create and initialize the button.
        JButton button = new JButton();
        
        //allows one action listener to listen for all buttons
        button.setActionCommand(toolTipText);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        if (imageURL != null) { //if image is found make a nice button                  
            button.setIcon(new ImageIcon(imageURL, imageName));
        } else { 
            button.setText(imageName);
        }
        return button;
    }
	
	/**
	 * makeMenuBar creates and returns a populated menubar.
	 */
	private JMenuBar makeMenuBar() {
		
        JMenu file, edit, help;
        JMenuItem menuItem;
        JMenuBar menubar=new JMenuBar();
 
 
        //Build the file menu.
        file = new JMenu("File");
        file.getAccessibleContext().setAccessibleDescription("Perform operations on files");
        menubar.add(file);
        
        //menu items for file (6)
        menuItem = new JMenuItem("New");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Creates a new file");
        menuItem.setToolTipText("Creates a new file");
        menuItem.setActionCommand("New");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
        menuItem = new JMenuItem("Open");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Opens a file from the disk");
        menuItem.setToolTipText("Opens a new file from the disk");
        menuItem.setActionCommand("Open");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
        menuItem = new JMenuItem("Save");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Saves current file");
        menuItem.setToolTipText("Saves current file");
        menuItem.setActionCommand("Save");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
        menuItem = new JMenuItem("Save As");
        //menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Saves current file to any directory");
        menuItem.setToolTipText("Saves current file to any directory");
        menuItem.setActionCommand("Save As");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
        file.addSeparator(); // nice line separating different things
        
        menuItem = new JMenuItem("Print");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Print the current file");
        menuItem.setToolTipText("Print the current file");
        menuItem.setActionCommand("Print");
        menuItem.addActionListener(this);
        file.add(menuItem);
        
        menuItem = new JMenuItem("Exit");
        menuItem.getAccessibleContext().setAccessibleDescription("Closes the program");
        menuItem.setToolTipText("Close the program");
        menuItem.setActionCommand("Exit");
        menuItem.addActionListener(this);
        file.add(menuItem);       

        //The edit bar 
        edit = new JMenu("Edit");
        edit.getAccessibleContext().setAccessibleDescription("Perform operations on your current file");
        menubar.add(edit);
        
        //the menu items for edit(4)
        menuItem = new JMenuItem("Cut");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Removes selected text and copies it to the clipboard");
        menuItem.setToolTipText("Removes selected text and copies it to the clipboard");
        menuItem.setActionCommand("Cut");
        menuItem.addActionListener(this);
        edit.add(menuItem);
        
        menuItem = new JMenuItem("Copy");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Copies selsected text to the clipboard");
        menuItem.setToolTipText("Copies selected text to the clipboard");
        menuItem.setActionCommand("Copy");
        menuItem.addActionListener(this);
        edit.add(menuItem);
        
        menuItem = new JMenuItem("Paste");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
        		"Puts the content of the clipboard to the right of the cursor");
        menuItem.setToolTipText("Puts the content of the clipboard to the right of the cursor");
        menuItem.setActionCommand("Paste");
        menuItem.addActionListener(this);
        edit.add(menuItem);
        
        menuItem = new JMenuItem("Select All");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Highlights all text from the current file");
        menuItem.setToolTipText("Highlights all text in the current file");
        menuItem.setActionCommand("Select All");
        menuItem.addActionListener(this);
        edit.add(menuItem);
 
        //The help bar
        help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_N);
        help.getAccessibleContext().setAccessibleDescription("Find help for using the current program");
        menubar.add(help);
        
        //menu items for help(2)
        menuItem = new JMenuItem("View Help");
        menuItem.getAccessibleContext().setAccessibleDescription("Opens a help window");
        menuItem.setToolTipText("Opens a help window");
        menuItem.setActionCommand("View Help");
        menuItem.addActionListener(this);
        help.add(menuItem);
        
        menuItem = new JMenuItem("About IDIOT IDE");
        menuItem.getAccessibleContext().setAccessibleDescription("Provides information about the IDE");
        menuItem.setToolTipText("Provides information about the IDE");
        menuItem.setActionCommand("About IDIOT IDE");
        menuItem.addActionListener(this);
        help.add(menuItem);
        
        return menubar;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {

		String action = e.getActionCommand();
	 
	    // See what menuItem was clicked and do the appropriate thing 
		//TODO find the appropriate thing to do, currently just prints a string
		switch(action){
			case "Copy":{ 
				System.out.println("Copy");
				break;
				
			}case "Cut":{
				System.out.println("Cut");
				break;
				
			} case "Paste":{ 
				System.out.println("Paste");
				break;
				
			} case "New":{ 
				
				//Open a tab with a blank window
				makeNewTab();
				break;
			
			} case "Open":{ 
				
				try {
					openNewTab(FileOpen.fileManager());
				} catch (FileNotFoundException | BadLocationException e1) {
					JOptionPane.showMessageDialog(null, "The file you selected could not be found.");	
				}
				
				break;
				
			} case "Print":{ 
				System.out.println("Print");
				break;
				
			} case "Save":{ 
				System.out.println("Save");
				break;
			
			} case "Save As":{
				System.out.println("Save As");
				break;
			
			} case "Exit":{

				//ask the user to save before closing 
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
				
				break;
				
			} case "Select All":{ 
				System.out.println("Select All");
				break;
			
			} case "View Help":{ 
				System.out.println("View Help");
				break;
				
			} case "About IDIOT IDE":{ 
				
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
				
				break;
				
			} case "Compile":{
				System.out.println("Compile");  
				break;
			}
		}
	}
	
	@Override
	public String toString() {
		return "This is a JFrame and a bunch of components";
	} 
}
