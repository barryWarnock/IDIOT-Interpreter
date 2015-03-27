package actions;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import main.GUI;
import main.TabButton;
import main.SyntaxHighlighter;
/**
 * 
 * @author bolster
 * This class creates a new tab with a blank window. The 
 * tab is put at the rightmost position in the tab listing 
 * and adds a close button to the tab 
 */
@SuppressWarnings("serial")
public class NewAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {

		JTabbedPane tabbedPane = GUI.getTabbedPane();
		
		//Create a scrolled text area to type into and add syntax highlighting 
		JTextPane IDIOT_file_content = new JTextPane(SyntaxHighlighter.SyntaxHighlighterProfile(0));
		
		
		//add a scrollPane to tabbedPane
		JScrollPane scroll = new JScrollPane(IDIOT_file_content);
		tabbedPane.add("new file",scroll);
		main.TextLineNumber lineNumbering = new main.TextLineNumber(IDIOT_file_content);
		scroll.setRowHeaderView( lineNumbering );
		
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
		
		//add null to the file path list, preventing save from working; only allow saveAs 
		GUI.getFilePathList().add(null);
	}
}
