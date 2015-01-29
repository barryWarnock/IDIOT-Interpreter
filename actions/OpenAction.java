package actions;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import main.FileOpen;
import main.TabButton;

@SuppressWarnings("serial")
public class OpenAction extends AbstractAction {

	private static JTabbedPane tabbedPane = main.GUI.tabbedPane;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//opens a new tab from a file
		try {
			openTab(FileOpen.fileManager());
		} catch (FileNotFoundException | BadLocationException e1) {
			JOptionPane.showMessageDialog(null, "The file you selected could not be found.");	
		} catch(Exception e2){}//this is only throw if the user selects cancel 
		

	}
	
	
	/**
	 * TODO this should have a close button on the edge of the tab
	 * @param file a file that you would like to open in a JEditorPane
	 * @throws BadLocationException, FileNotFoundException
	 */
	public void openTab(File file) throws BadLocationException, FileNotFoundException 
	{
		System.out.println("test");/////////////////
		//Create a scrolled text area to type into
		JEditorPane IDIOT_file_content = new JEditorPane();
		IDIOT_file_content.setEditable(true);
			
		// scans file into the JEditPane
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			Document doc = IDIOT_file_content.getDocument();
			doc.insertString(doc.getLength(), line+"\n", null);
			
		}
		scan.close();
		
		System.out.println("test");//////////////////////
		JScrollPane scroll = new JScrollPane(IDIOT_file_content);
		tabbedPane.add(file.getName(),scroll);
		
		//newest tabs spawn to the right, find the newest's index
		int index = (tabbedPane.getTabCount() - 1);
		System.out.println(index);/////////////////////////
		
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

}
