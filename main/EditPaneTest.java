package main;



import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;

public class EditPaneTest extends JFrame {
	
    private int findLastNonWordChar (String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    public EditPaneTest () {
    	
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        final StyleContext cont = StyleContext.getDefaultStyleContext();
        final AttributeSet attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
        final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches("(\\W)*(ADD|GOTO|START|END)"))
                            setCharacterAttributes(wordL, wordR - wordL, attr, false);
                        else
                            setCharacterAttributes(wordL, wordR - wordL, attrBlack, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            public void remove (int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("(\\W)*(ADD|START|END|GOTO)")) {
                    setCharacterAttributes(before, after - before, attr, false);
                } else {
                    setCharacterAttributes(before, after - before, attrBlack, false);
                }
            }
        };
        JTextPane txt = new JTextPane(doc);
        add(new JScrollPane(txt));
        setVisible(true);
    }

    public static void main(String... args)
    {
    
                    new EditPaneTest();
                
            
    }
}
/*
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class EditPaneTest extends JFrame
{
    private JPanel topPanel;
    private JEditorPane editPane;

    public EditPaneTest()
    {
        topPanel = new JPanel();        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         


        editPane = new JEditorPane();                

        topPanel.add(editPane);

        getContentPane().add(topPanel);

        pack();
        setVisible(true);   
    }

    private void colorChange(JEditorPane ep)
    {
    	//while(the edit pane has next string, or is split )
    	final String[] defaults= {"ADD","START","END","GOTO"};
    	StyledDocument sd = (StyledDocument) tp.getDocument();
    	for (int i=0; i<defaults.length;i++){
    		//if(the split string is equal to one of the defaults)
    		//color it
    		//and replace it
            int len = ep.getDocument().getLength();
            ep.setCaretPosition(len);
           // ep.replaceSelection()?;
    		
    	}


    }

    public static void main(String... args)
    {
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    new EditPaneTest();
                }
            });
    }
} */
