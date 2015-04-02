package actions;
import main.GUI;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/**
 * 
 *
 * This action prints the open file to a printer.
 *  @author bolster
 */
@SuppressWarnings("serial")
public class PrintAction extends AbstractAction 
{
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		try {
			GUI.getFocusTextPane().print();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
