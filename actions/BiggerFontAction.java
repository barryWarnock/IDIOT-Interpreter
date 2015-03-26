package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import main.SyntaxHighlighter;
import main.GUI;
/**
 * 
 * @author bolster
 * This Action makes font bigger for the text editor.
 */
@SuppressWarnings("serial")
public class BiggerFontAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JTabbedPane tabbed = GUI.getTabbedPane();
		
		for(int i=0; i<tabbed.getComponentCount(); i++){
			//returns the tab component with focus
			JScrollPane scroll = (JScrollPane) tabbed.getComponentAt(0);
			if(scroll!=null){
				JTextPane txt = (JTextPane) scroll.getComponent(0).getComponentAt(100, 100);
				if(txt!=null){
					String words = null;
					try {
						words = txt.getDocument().getText(0, txt.getDocument().getLength());
					} catch (BadLocationException e2) {
						e2.printStackTrace();
					}
					txt.setDocument(SyntaxHighlighter.SyntaxHighlighterProfile(1));
					try {
						txt.getDocument().insertString(0, words, null);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
}