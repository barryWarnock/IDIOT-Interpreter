package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
/**
 * 
 * This Action opens a window with credits about the authors.
 * @author bolster
 */
@SuppressWarnings("serial")
public class AboutAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//open a message window
		JOptionPane.showMessageDialog(null, "The IDIOT editor was made by:\n\n" 
				+ "Jeremy Bolster,\n"
				+ "Travis Kurucz,\n"
				+ "Timothy Hillier,\n"
				+ "Dean Kutin,\n"
				+ "Barry Warnock\n\n",
				"About the IDIOT editor",JOptionPane.PLAIN_MESSAGE,null);
		
	}

}
