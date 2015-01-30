package actions;
import main.*;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class CompileAction extends AbstractAction {

	private JTabbedPane tabbedPane = GUI.getTabbedPane();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//returns the tab component with focus
		JScrollPane scroll = (JScrollPane) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
		if(scroll==null){
			//TODO make a popup error message
			System.out.println("null :(");
		}else{
			final JEditorPane editor = (JEditorPane) scroll.getComponent(0).getComponentAt(100, 100);
			if(editor==null){
				//TODO make a popup error message
				System.out.println("null :(");
			}else{
				
				//TODO this doesn't need to be in a thread because interpreter is already it's own thread
				//opens a new thread so infinite loops don't freeze the gui
				Thread thread = new Thread() {
			        public void run() {
			        	//sends a string to the interpreter
						MainRun.getInterpreter().run(editor.getText());
			        }
			    };
			    thread.start();
			}
		}
	}

}
