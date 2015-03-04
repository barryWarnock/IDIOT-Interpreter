package main;
import javax.swing.*;

/**
 * Created by Dean Kutin
 */
public class HintRun {

    /**
     * @author bolster
     * @deprecated move to helpfulhints
     * TODO move to helpful hints 
     * TODO open in the center of the IDE
     */
    public HintRun()
    {
    	HelpfulHints frame = new HelpfulHints();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setVisible(true);
    }
}
