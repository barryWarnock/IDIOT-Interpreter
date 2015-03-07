package main;
/**
 * @author Dean Kutin
 */

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;



@SuppressWarnings("serial")
public class HelpfulHints extends JFrame{

    private JTextArea area; //This is my textarea to display hints
    private JPanel panel;
    private JButton close, previous, next; //Three buttons to operate program
    private List <String> tips = new ArrayList<String>();
    private int displayedTipIndex = 0;

    public HelpfulHints(){

        super("Tip of the Day");
        // set up your tips
        tips.add("First Tip");
        tips.add("Second Tip");
        tips.add("Third Tip");
        setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

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
        area.setEditable(true);
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