package main;
/**
 * @author Dean Kutin
 * TODO add checkbox that creates a file within the app to never show again
 * TODO add a settings menu bar item to show the helpfulhints again
 */

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;



@SuppressWarnings("serial")
public class HelpfulHints extends JFrame{

    private JTextArea area; //This is my TextArea to display hints
    private JPanel panel;
    private JButton close, previous, next; //Three buttons to operate program
    private JCheckBox showtipsonstartup; //CheckBox that allows you to turn off helpful hints for future loadups
    private List <String> tips = new ArrayList<String>();
    private int displayedTipIndex = 0;

    public HelpfulHints(){

        super("Tip of the Day");
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400,400);
        setVisible(true);
        setLocationRelativeTo(null);
        
        // These are the tips that will appear in tip GUI
        tips.add("You can hover over an icon in the menu bar to find out what\n"
        + "the button does.");
        tips.add("Sample Program:\n"
        + "START\n"
        + "CMT This is our Helpful Hints GUI\n"
        + "VAR g\n"
        + "VAR t\n"
        + "VAR s\n"
        + "ASSIGN g 5\n"
        + "ASSIGN s 0\n"
        + "PRINT (Enter a value for t)\n"
        + "ENTER t\n"
        + "ADD g t s\n"
        + "PRINT s\n"
        + "END");
        tips.add("After hitting the compiler button, your output will be displayed\n"
        + "in the console frame at the bottom of the screen.");
        tips.add("Closing the program will allow you to Exit Without Saving, or\n"
        + "Save and Exit the IDIOT Program. ");
        tips.add("The menu bar contains buttons that allow you to, Save, Open,\n"
        + "Create New Project, Copy, Cut, Paste, Print, Compile, Bigger \n"
        + "Font, Smaller Font");
        setLayout(new BorderLayout());

        //Create panel and FlowLayout
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        //Create checkbox 
        showtipsonstartup = new JCheckBox("Disable Tips");
        add(showtipsonstartup);
       
        //Create first button
        close= new JButton("Close");
        add(close);

        //Create second button
        previous = new JButton("Previous");
        add(previous);

        //Create third button
        next = new JButton("Next");
        add(next);

        //Add buttons to panel
        panel.add(showtipsonstartup);
        panel.add(close);
        panel.add(previous);
        panel.add(next);
        

        //Keep buttons to south of panel
        add(panel, BorderLayout.SOUTH);

        //Create ButtonHandler for button event handling
        ButtonHandler handler = new ButtonHandler();
        close.addActionListener(handler);
        previous.addActionListener(handler);
        next.addActionListener(handler);

        //Create the helpful hint area on the screen
        area = new JTextArea();
        area.setEditable(false);
        area.setLayout(new FlowLayout(FlowLayout.CENTER));

        //Set first tip
        area.setText(tips.get(displayedTipIndex));
        add(area, BorderLayout.CENTER);

        //Disable previous button when we start
        previous.setEnabled(false);

        //Set visible so can be seen
        setVisible(true);

    }

    //Inner class for button event handling
    private class ButtonHandler implements ActionListener {

        //handle Button event
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == next) {
                displayedTipIndex++;
                area.setText(tips.get(displayedTipIndex));
                // disable the next button if no more tips
                if (displayedTipIndex >= tips.size() - 1) {
                    next.setEnabled(false);
                }
                // re-enable previous button
                previous.setEnabled(true);
            }
            if (e.getSource() == previous) {
                displayedTipIndex--;
                area.setText(tips.get(displayedTipIndex));
                /// disable the previous button if no more tips
                if (displayedTipIndex < 1) {
                    previous.setEnabled(false);
                }
                // re-enable next button
                next.setEnabled(true);
            }
            if (e.getSource() == close) {
            dispose();
            }
        }
    }
}