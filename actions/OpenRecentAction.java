package actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CancellationException;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;

@SuppressWarnings("serial")
public class OpenRecentAction extends OpenAction 
{
	String filePath=null;
	public OpenRecentAction(String str)
	{
		filePath=str;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		// Opens the file chosen in a new tab. 
		try {
			this.openTab(new File(filePath));
		} catch (FileNotFoundException | BadLocationException e1) {
			JOptionPane.showMessageDialog(null,
					"The file you selected could not be found.");
		} catch (CancellationException e2) {
		}// this is only thrown if the user selects cancel
		catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
