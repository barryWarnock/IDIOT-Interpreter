package main;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
/**
 * @author bolster
 * @version 0.0.1
 * TODO implement tabs 
 * TODO implement a compile button
 * TODO implement an output terminal
 * TODO (feature request) Give menu items some icons for cut and paste
 */

public class GUI
{
	
	/**
	 * This method creates a populated frame 
	 * @param name is the name of the window that you wish to create
	 * @param x is the minimum width of the window
	 * @param y is the minimum height of the window
	 */
	public void createFrame(String name, int width, int height)
	{
        JFrame frame = new JFrame(name);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(width, height));
        frame.setSize(2*width,2*height);
        frame.setLocationRelativeTo(null);
        
       //Create and set up the content pane (Menu and tabs and output and textfield)
        GUI content = new GUI();
        frame.setJMenuBar(new MainMenuBar());
        frame.setContentPane(content.createPanel(height));
        frame.setVisible(true);
        
	}

	

	//TODO this should take a text file and place it in the text field
	public JComponent makeNewTab() {
		JPanel panel = new JPanel(false);
		        
		//Create a scrolled text area to type into
		JEditorPane IDIOT_file_content = new JEditorPane();
		IDIOT_file_content.setEditable(true);
		JScrollPane scroll = new JScrollPane(IDIOT_file_content);
		        
		panel.setLayout(new BorderLayout());
		panel.add(scroll);
		return panel;
	}
		
    public Container createPanel(int dividerLocation) {
    	
    	Dimension minimumSize=new Dimension(100,100);
    	
    	//create the panel that will be returned 
    	JPanel finalPanel=new JPanel(new BorderLayout());
    	
        
        //create the tabbedPane
        //TODO this should happen dynamically to add new tabs when opening files and close them when they should be closed
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setMinimumSize(minimumSize);
        JComponent panel1 = makeNewTab();
        JComponent panel2 = makeNewTab();
        JComponent panel3 = makeNewTab();
		tabbedPane.addTab("File Name Goes Here", null, panel1);
		tabbedPane.addTab("File 2 Name Goes Here", null, panel2);
		tabbedPane.addTab("File 3 Name Goes Here", null, panel3);
		
        
		//create the terminal text area and give it a label 
        JTextArea output=new JTextArea();
        output.setMinimumSize(minimumSize);
        JPanel terminalPanel=new JPanel(new BorderLayout());
        terminalPanel.add(output);
        output.setBorder(BorderFactory.createTitledBorder(null, "console"));
        
        
        //create the toolbar and add it to the top of the panel
        Toolbar toolbar=new Toolbar();
        finalPanel.add(toolbar, BorderLayout.NORTH);
        
        //Create the split pane 
    	JSplitPane contentPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    	contentPane.setOneTouchExpandable(true);
        contentPane.setOpaque(true);
        
        //add components to the split pane
        contentPane.add(tabbedPane);
        contentPane.add(terminalPanel);
        contentPane.setDividerLocation(dividerLocation);
        
        //add the splitpane to the panel
        finalPanel.add(contentPane, BorderLayout.CENTER);
        return finalPanel;
    }

    
}
