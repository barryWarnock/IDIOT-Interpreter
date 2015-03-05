package main;
import javax.swing.*;

/**
 * @author Dean Kutin
 */
public class HintRun {

    /**
     * @author bolster
     * @deprecated 
     * TODO move to helpful hints
     */
    public HintRun()
    {
    	HelpfulHints frame = new HelpfulHints();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
