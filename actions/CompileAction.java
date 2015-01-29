package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class CompileAction extends AbstractAction {

	private JTabbedPane tabbedPane = main.GUI.tabbedPane;
	private main.Interpreter interpreter = main.GUI.interpreter;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//returns the tab component with focus
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
				
				
				//opens a new thread so infinite loops don't freeze the gui
				Thread thread = new Thread() {
			        public void run() {
			        	//sends a string to the interpreter
						interpreter.run(editor.getText());
			        }
			    };
			    thread.start();
			}
		}
	}

}
