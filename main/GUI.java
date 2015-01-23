package main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
/**
 * @author bolster
 * @version 0.0.1
 * This creates a GUI complete with a menubar and a toolbar
 * <p>
 * There is no hashCode or equals methods for this class as there are no static variables or objects.
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
        JComponent blankPanel=null;
		try {
			//The URL is fake but it can't be null otherwise the JEditPane is uneditable
			blankPanel = makeNewTab(new URL("http://example.com/hello%20world...blablabla"));
		} catch (MalformedURLException e) {}
		tabbedPane.addTab("New File", null, blankPanel);

        
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
    public JPanel createPanel(int dividerLocation) {
    	
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
	 * TODO this should take a text file and place it in the text field
	 * TODO this should have a close button on the edge of the tab
	 * @return JPanel with a JEditorPane in a JScrollPane
	 */
	public JComponent makeNewTab(URL url) {
		JPanel panel = new JPanel(false);
		        
		//Create a scrolled text area to type into
		JEditorPane IDIOT_file_content = new JEditorPane();
		try {
			IDIOT_file_content.setPage(url);
		} catch (IOException e) {
			//TODO file not found :{
			//should give a message dialog and tell the user it wasn't found
		}
		IDIOT_file_content.setEditable(true);
		JScrollPane scroll = new JScrollPane(IDIOT_file_content);
		        
		panel.setLayout(new BorderLayout());
		panel.add(scroll);
		return panel;
	}
	
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
		private JMenuBar makeMenuBar() {
		
        JMenu file, edit, help;
        JMenuItem menuItem;
        JMenuBar menubar=new JMenuBar();
 
 
        //Build the file menu.
        file = new JMenu("File");
        file.getAccessibleContext().setAccessibleDescription("Perform operations to do with files");
        menubar.add(file);
        
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
        menubar.add(edit);
        
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
        menubar.add(help);
        
        //menu items for help(2)
        menuItem = new JMenuItem("View Help");
        menuItem.getAccessibleContext().setAccessibleDescription("Opens a help window");
        menuItem.setActionCommand("View Help");
        menuItem.addActionListener(this);
        help.add(menuItem);
        
        menuItem = new JMenuItem("About My IDE");
        menuItem.getAccessibleContext().setAccessibleDescription("Provides information about the IDE");
        menuItem.setActionCommand("About My IDE");
        menuItem.addActionListener(this);
        help.add(menuItem);
        
        return menubar;
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
			//Open a tab with a blank window
			JComponent blankPanel=null;
			try {
				//The URL is fake but it can't be null otherwise the JEditPane is uneditable
				blankPanel = makeNewTab(new URL("http://example.com/hello%20world...blablabla"));
			} catch (MalformedURLException ex) {}
			tabbedPane.addTab("New File", null, blankPanel);
			
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
			
		} else if ("Compile".equals(action)) 
		{ 
			System.out.println("Compile");   
		}
	}
	
	@Override
	public String toString() {
		return "This is a JFrame and a bunch of components";
	} 
}
