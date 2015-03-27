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
		//increase the font size 
		SyntaxHighlighter.SyntaxHighlighterProfile(1);
		
		for(int i=0; i<tabbed.getComponentCount(); i++){
			//returns all tabs
			JScrollPane scroll = (JScrollPane) tabbed.getComponentAt(i);
			if(scroll!=null){
				JTextPane txt = (JTextPane) scroll.getComponent(0).getComponentAt(100, 100);
				if(txt!=null){
					String words = null;
					try {
						//store the words temporarily as a string
						words = txt.getDocument().getText(0, txt.getDocument().getLength());
					} catch (BadLocationException e2) {
						e2.printStackTrace();
					}
					//update the document to the larger font size one
					txt.setDocument(SyntaxHighlighter.SyntaxHighlighterProfile(0));
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