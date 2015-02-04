package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import actions.*;

import javax.swing.*;



/**
 * @author bolster
 * @version 0.0.1
 * This creates a GUI complete with a menubar and a toolbar
 * <p>
 * There is no hashCode or equals methods for this class as there are no static variables or objects.
 * As well there is no constructor because this class does not need to initialize anything.
 * TODO make all actions into abstract actions and then try to move menubar and toolbar and 
 * tab making out of this class
 */
public class GUI implements ActionListener
{
	//this allows actionListeners to call tabbedPane.makeNewTab(); and the interpreter 
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
        //I added to this line as well
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
        
        //add output to interpreter
        MainRun.getInterpreter().setIo(output);
        
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
	 */
	public void makeNewTab() 
	{
		
		//Create a scrolled text area to type into
		JEditorPane IDIOT_file_content = new JEditorPane();
		IDIOT_file_content.setEditable(true);
		
		//add a scrollPane to tabbedPane
		JScrollPane scroll = new JScrollPane(IDIOT_file_content);
		tabbedPane.add("new file",scroll);
		
		//newest tabs spawn to the right, find the newest's index
		int index = (tabbedPane.getTabCount() - 1);
		
		//create a panel for the button and label
		JPanel nameAndButton = new JPanel(new GridBagLayout());
		nameAndButton.setOpaque(false);
		
		//make the label and button 
		JLabel tabTitle = new JLabel("new file");
		JButton closeButton = new TabButton(tabbedPane);

		//Do some funky stuff with the layout manager to make everything appear nice
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		
		nameAndButton.add(tabTitle, gbc);
		//after adding the label adjust the location so the button is put in the right place	
		gbc.gridx++;
		gbc.weightx = 0;
		nameAndButton.add(closeButton, gbc);
		//put the fancy pane on the right tab
		tabbedPane.setTabComponentAt(index, nameAndButton);
		
		
	}
	
	/**
	 * 
	 * @return JToolBar full of different widgets 
	 */
	private JToolBar makeToolbar()
	{
		JToolBar toolbar=new JToolBar(JToolBar.HORIZONTAL);
		toolbar.setFloatable(true);
		
		JButton tempButton;
		
		//add buttons 
		//TODO give buttons actionListeners
		
		tempButton = makeButton("saveIcon", "Save");
		tempButton.addActionListener(new SaveAction());
		toolbar.add(tempButton);
		
		tempButton = makeButton("openIcon", "Open");
		tempButton.addActionListener(new OpenAction());
		toolbar.add(tempButton);
		
		toolbar.add(makeButton("newIcon", "New"));
        toolbar.add(makeButton("copyIcon", "Copy"));
        toolbar.add(makeButton("cutIcon", "Cut"));
        toolbar.add(makeButton("pasteIcon", "Paste"));
        
        tempButton = makeButton("printIcon", "Print");
        tempButton.addActionListener(new PrintAction());
        toolbar.add(tempButton);
        
        tempButton = makeButton("compileIcon", "Compile");
        tempButton.addActionListener(new CompileAction());
        toolbar.add(tempButton);
        
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
        button.setToolTipText(toolTipText);
        
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
        menuItem.addActionListener(new actions.OpenAction());
        file.add(menuItem);
        
        menuItem = new JMenuItem("Save");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Saves current file");
        menuItem.setToolTipText("Saves current file");
        menuItem.addActionListener(new SaveAction());
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
        menuItem.addActionListener(new PrintAction());
        file.add(menuItem);
        
        menuItem = new JMenuItem("Exit");
        menuItem.getAccessibleContext().setAccessibleDescription("Closes the program");
        menuItem.setToolTipText("Close the program");
        menuItem.addActionListener(new ExitAction());
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
        menuItem.addActionListener(new AboutAction());
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
			
			} case "Save As":{
				try {
					FileOpen.fileSaveAs(tabbedPane);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					//cry????
					e1.printStackTrace();
				}
				break;
			
			} case "Select All":{ 
				System.out.println("Select All");
				break;
			
			} case "View Help":{ 
				System.out.println("View Help");
				break;
				
			} 
		}
	}

	public static JTabbedPane getTabbedPane()
	{
		return tabbedPane;
	}
	
	@Override
	public String toString() {
		return "This is a JFrame and a bunch of components";
	} 
}
