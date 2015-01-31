package actions;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class AboutAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//try to find a nice icon for the dialog box but don't display it if it can't be found
		ImageIcon icon=null;
		URL imageURL = this.getClass().getResource("/images/infoIcon.png");
		if (imageURL != null) {                   
			icon=new ImageIcon(imageURL,"Info");
        } 
		
		JOptionPane.showMessageDialog(null, "The IDIOT editor was made by:\n\n" 
				+ "Jeremy Bolster,\n"
				+ "Travis Kurucz,\n"
				+ "Timothy Hillier,\n"
				+ "Dean Kution,\n"
				+ "Barry Warnock\n\n"
				+ "Thanks to Visual Pharm for the icons which were released under\n"
				+ "Creative Commons Attribution-No Derivative Works 3.0 Unported\n"
				+ "They can be viewed at https://www.iconfinder.com/icons/27860/",
				"About the IDIOT editor",JOptionPane.PLAIN_MESSAGE,icon);
		
	}

}
