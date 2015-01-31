package actions;
import main.GUI;

import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class PrintAction extends AbstractAction {

	private JTabbedPane tabbedPane = GUI.getTabbedPane();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JScrollPane scroll = (JScrollPane) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		if(scroll==null){
			//TODO make a popup error message
			System.out.println("null :(");
		}else{
			JEditorPane editor = (JEditorPane) scroll.getComponent(0).getComponentAt(100, 100);
			if(editor==null){
				//TODO make a popup error message
				System.out.println("null :(");
			}else{
				try {
					editor.print();
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
				}
			}
		}
	}

}
