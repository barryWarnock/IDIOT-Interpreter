package main;
import javax.swing.*;

/**
 * Created by Dean Kutin
 */
public class HintRun {

    public static void hintthing(){

        //Create the GUI for viewing
        HelpfulHints frame = new HelpfulHints();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setVisible(true);

    }
}
