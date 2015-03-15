package main;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

import actions.*;

import javax.swing.*;



/**
 * @author bolster
 * @version 0.1.0
 * This creates a GUI complete with a menubar and a toolbar
 * <p>
 * There is no hashCode or equals methods for this class as there are no static variables or objects.
 * As well there is no constructor because this class does not need to initialize anything.
 */
public class GUI {
	
	//this allows actionListeners to call tabbedPane.makeNewTab(); and the interpreter 
	private static JTabbedPane tabbedPane = new JTabbedPane();
	private static ArrayList<String> tabFilePath = new ArrayList<String>(20);
	
	/**
	 * This method creates a populated frame 
	 * @param name is the name of the window that you wish to create
	 * @param x is the minimum width of the window
	 * @param y is the minimum height of the window
	 */
	@SuppressWarnings("unused")
	private GUI (){}
	
	public GUI (String name, int width, int height)
	{
        final JFrame frame = new JFrame(name);

        //initialize the frameC
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //asks to save when the program is closed
   
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            	
            	Object[] options = {"Save and Exit", "Exit Without Saving","Cancel"};

        		int result = JOptionPane.showOptionDialog(frame, "Do you want to save before you close?","Close Dialog",
        		    JOptionPane.YES_NO_CANCEL_OPTION,  JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
            	
            	
        		if (result == JOptionPane.YES_OPTION)
        		{
        			//call the save action
        			//Popup and ask if they want to save.
        			SaveAction save = new SaveAction();
        			save.actionPerformed(new ActionEvent(save, 1, ""));
        			
        			//TODO make sure that it actually saves before closing 
        			System.exit(0);
        		
        		} else if(result == JOptionPane.NO_OPTION)
        		{
        			System.exit(0);	
        		} 
        	}

        });
        frame.setMinimumSize(new Dimension(width, height));
        frame.setSize(2*width,2*height);
        frame.setLocationRelativeTo(null);

        //Open a tab with a blank window
        NewAction tab1 = new NewAction();
        tab1.actionPerformed(new ActionEvent(tab1, 1, ""));
        
       //Create and set up the content pane (Menu and tabs and output and textfield)
        frame.setJMenuBar(makeMenuBar());
        //I added to this line as well
        frame.setContentPane(createPanel(height));
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
        JScrollPane outputScroll = new JScrollPane(output);
        terminalPanel.add(outputScroll);
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
        
        //add the split pane and tool bar to the panel
        finalPanel.add(makeToolbar(), BorderLayout.NORTH);
        finalPanel.add(contentPane, BorderLayout.CENTER);
        return finalPanel;
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
		
		tempButton = makeButton("saveIcon", "Save");
		tempButton.addActionListener(new SaveAction());
		toolbar.add(tempButton);
		
		tempButton = makeButton("openIcon", "Open");
		tempButton.addActionListener(new OpenAction());
		toolbar.add(tempButton);
		
		tempButton = makeButton("newIcon", "New");
		tempButton.addActionListener(new NewAction());
		toolbar.add(tempButton);
		
        tempButton = makeButton("copyIcon", "Copy");
        tempButton.addActionListener(new CopyAction());
        toolbar.add(tempButton);
        
        tempButton = makeButton("cutIcon", "Cut");
        tempButton.addActionListener(new CutAction());
        toolbar.add(tempButton);
        
        tempButton = makeButton("pasteIcon", "Paste");
        tempButton.addActionListener(new PasteAction());
        toolbar.add(tempButton);
        
        tempButton = makeButton("printIcon", "Print");
        tempButton.addActionListener(new PrintAction());
        toolbar.add(tempButton);
        
        tempButton = makeButton("compileIcon", "Compile");
        tempButton.addActionListener(new CompileAction());
        toolbar.add(tempButton);
        
        tempButton = makeButton("BiggerFontIcon", "BiggerFont");
        tempButton.addActionListener(new BiggerFontAction());
        toolbar.add(tempButton);
        
        tempButton = makeButton("SmallerFontIcon", "SmallerFont");
        tempButton.addActionListener(new SmallerFontAction());
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
        menuItem.addActionListener(new NewAction());
        file.add(menuItem);
        
        menuItem = new JMenuItem("Open");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Opens a file from the disk");
        menuItem.setToolTipText("Opens a new file from the disk");
        menuItem.addActionListener(new OpenAction());
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
        menuItem.addActionListener(new SaveAsAction());
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
        menuItem.addActionListener(new CutAction());
        edit.add(menuItem);
        
        menuItem = new JMenuItem("Copy");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Copies selsected text to the clipboard");
        menuItem.setToolTipText("Copies selected text to the clipboard");
        menuItem.addActionListener(new CopyAction());
        edit.add(menuItem);
        
        menuItem = new JMenuItem("Paste");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
        		"Puts the content of the clipboard to the right of the cursor");
        menuItem.setToolTipText("Puts the content of the clipboard to the right of the cursor");
        menuItem.addActionListener(new PasteAction());
        edit.add(menuItem);
        
        menuItem = new JMenuItem("Select All");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Highlights all text from the current file");
        menuItem.setToolTipText("Highlights all text in the current file");
        menuItem.addActionListener(new SelectAllAction());
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
        menuItem.addActionListener(new HelpAction());
        help.add(menuItem);
        
        menuItem = new JMenuItem("About IDIOT IDE");
        menuItem.getAccessibleContext().setAccessibleDescription("Provides information about the IDE");
        menuItem.setToolTipText("Provides information about the IDE");
        menuItem.addActionListener(new AboutAction());
        help.add(menuItem);
        
        return menubar;
    }
	
	/**
	 * @return JTabbedPane containing the content tabs
	 */
	public static JTabbedPane getTabbedPane()
	{
		return tabbedPane;
	}
	
	/**
	 * @return ArrayList<String> an arrayList of the absolute file paths of the 
	 * files stored in different tabs
	 */
	public static ArrayList<String> getFilePathList()
	{
		return tabFilePath;
	}

	
	/**
	 * @return JTextPane of the in-focus tab
	 */
	public static JTextPane getFocusTextPane() throws Exception{
		//returns the tab component with focus
		JScrollPane scroll = (JScrollPane) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		if(scroll==null){
			throw new Exception();
		}else{
			JTextPane txt = (JTextPane) scroll.getComponent(0).getComponentAt(100, 100);
			if(txt==null){
				throw new Exception();
			}else{
				return txt;
			}
		}
		
	}
	
	@Override
	public String toString() {
		return "This is a JFrame and a bunch of components";
	} 
	
}
