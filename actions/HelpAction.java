package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import main.HelpfulHints;
/**
 * 
 * @author bolster
 * This Action Opens the helpful hints dialog.
 */
@SuppressWarnings("serial")
public class HelpAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		new HelpfulHints(false);
	}

}
