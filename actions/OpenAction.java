package actions;

import main.GUI;
import main.SyntaxHighlighter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CancellationException;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import main.TabButton;

/**
 * This Action opens a file into a new tab.
 *
 */
@SuppressWarnings("serial")
public class OpenAction extends AbstractAction {

	private JTabbedPane tabbedPane = GUI.getTabbedPane();

	@Override
	public void actionPerformed(ActionEvent e) {

		// Opens the file chosen in a new tab. 
		try {
			openTab(fileManager());
		} catch (FileNotFoundException | BadLocationException e1) {
			JOptionPane.showMessageDialog(null,
					"The file you selected could not be found.");
		} catch (CancellationException e2) {
		}// this is only thrown if the user selects cancel
		catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * This opens the file through the file manager
	 * 
	 * @return The file that the user chose
	 * @throws CancellationException if the user cancels
	 */
	protected File fileManager() throws CancellationException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int option = fileChooser.showOpenDialog(fileChooser);

		

		if (option == JFileChooser.APPROVE_OPTION) {
			// user selects file,return the selected file to whatever called

			File selectedFile = fileChooser.getSelectedFile();

			return selectedFile;

		} else {
			throw new CancellationException();
		}
	}

	/**
	 * @param file a file that you would like to open in a JEditorPane
	 * @throws BadLocationException, FileNotFoundException
	 * @throws IOException
	 */
	protected void openTab(final File file) throws BadLocationException,
			IOException {
		// Create a scrolled text area to type into
		final JTextPane IDIOT_file_content = new JTextPane(
				SyntaxHighlighter.SyntaxHighlighterProfile(0));

		// puts the file into the JTextPane
		//runs in a new Thread

		final Thread thread = new Thread() {
			public void run() {
				try (BufferedReader br = new BufferedReader(
						new FileReader(file))) {
					String line;
					while ((line = br.readLine()) != null) {
						Document doc = IDIOT_file_content.getDocument();
						try {
							doc.insertString(doc.getLength(), line + "\n", null);
						} catch (BadLocationException e) {
							e.printStackTrace();
						}

					}

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};
		thread.start();

		// Add line numbering to the scrollpane
		JScrollPane scroll = new JScrollPane(IDIOT_file_content);
		main.TextLineNumber lineNumbering = new main.TextLineNumber(
				IDIOT_file_content);
		scroll.setRowHeaderView(lineNumbering);

		// remove .IDIOT extension for cleaner UI
		String name = file.getName();
		if (name.endsWith(".IDIOT"))
			name = name.substring(0, file.getName().indexOf(".IDIOT"));

		tabbedPane.add(file.getName(), scroll);

		// newest tabs spawn to the right, find the newest's index
		int index = (tabbedPane.getTabCount() - 1);

		// create a panel for the button and label
		JPanel nameAndButton = new JPanel(new GridBagLayout());
		nameAndButton.setOpaque(false);

		// make the label and button
		JLabel tabTitle = new JLabel(name);
		JButton closeButton = new TabButton(tabbedPane);

		// Do some funky stuff with the layout manager to make everything appear
		// real beautiful
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;

		nameAndButton.add(tabTitle, gbc);
		// after adding the label adjust the location so the button is put in
		// the right place
		gbc.gridx++;
		gbc.weightx = 0;
		nameAndButton.add(closeButton, gbc);
		// put the fancy pane on the right tab
		tabbedPane.setTabComponentAt(index, nameAndButton);

		// add the file path to a list for use when saving and add it to recent open files
		GUI.addRecentFile(file.getAbsolutePath(), name);
		GUI.getFilePathList().add(file.getAbsolutePath());

	}
}
