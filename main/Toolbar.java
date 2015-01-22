package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * 
 * @author bolster
 * @version 0.1.0
 * Creates and returns a populated toolbar for the IDE.
 * <p>
 * The toolbar is floatable by default. This is a simple toolbar, it has one method for making buttons with 
 * names, action listeners, tooltip text and, action command names. The action listener method implemented 
 * looks for the unique action command of each button before performing the appropriate task. 
 * <p>
 * There is no hashCode or equals methods for this class as there are no variables.
 * 
 */

@SuppressWarnings("serial")
public class Toolbar extends JToolBar implements ActionListener 
{
	
	/**
	 * 
	 * @param floatable This parameter makes the toolbar float if true or not if false.
	 */
	public Toolbar(boolean floatable)
	{
		super(JToolBar.HORIZONTAL);
		setFloatable(floatable);
		
		//add buttons 
		add(makeButton("saveIcon", "Save"));
		add(makeButton("openIcon", "Open"));
		add(makeButton("newIcon", "New"));
        add(makeButton("copyIcon", "Copy"));
        add(makeButton("cutIcon", "Cut"));
        add(makeButton("pasteIcon", "Paste"));
        add(makeButton("printIcon", "Print"));
        add(makeButton("compileIcon", "Compile"));
		
	}
	
	/**
	 * simple no argument constructor for the toolbar. Makes the toolbar non-floatable by default
	 */
	public Toolbar()
	{
		this(false);
	}

	/**
	 * 
	 * @param imageName the name of the image as a file 
	 * @param toolTipText the rollover text and also the Action Command argument 
	 * @return JButton complete with action listener, tooltipText, action command and, an icon
	 */
	protected JButton makeButton(String imageName, String toolTipText)
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
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		String action = e.getActionCommand();
	 
	    // See what button was clicked and do the appropriate thing 
		//TODO find the appropriate thing to do, currently just prints a string
		if ("Copy".equals(action)) 
		{ 
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
			
		} else if ("Compile".equals(action)) 
		{ 
			System.out.println("Compile");   
		}
	}
	//TODO make this into a list of toolbar items
	@Override
	public String toString()
	{
		return "This is a toolbar";
	}
	
	
}
