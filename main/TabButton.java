package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * See bottom for copyright notice from Oracle 
 * @author bolster
 * This is a custom button for tabs in a JTabbedPane. A large portion of the code for
 * this button was obtained directly from Oracle. 
 */
@SuppressWarnings("serial")
public class TabButton extends JButton implements ActionListener 
{
	private JTabbedPane pane;
	
    public TabButton(JTabbedPane pane) 
    {
    	//label the button 'X'
    	super("X");
    	this.pane=pane;
    	
    	//do the fancy stuff
    	setForeground(Color.RED);
        int size = 17;
        setPreferredSize(new Dimension(size, size));
        setToolTipText("close this tab");
        
        
        setContentAreaFilled(false);
        
        //No need to be focusable
        setFocusable(false);
       
        //Make the button look like a button
        setUI(new BasicButtonUI());
        //Make it transparent
        setContentAreaFilled(false);
        //give it a cool border
        setBorder(BorderFactory.createEtchedBorder());
        setBorderPainted(false);
        
        //Making nice rollover effect
        addMouseListener(buttonMouseListener);
        setRolloverEnabled(true);
        //Close the proper tab by clicking the button
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int i = pane.indexOfTabComponent(this.getParent());
        if (i != -1) {
            pane.remove(i);
            //TODO ask the user to save the tab before closing 
        }
    }
    
    //Makes a border appear when the mouse is rolled over the button
    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }
 
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };

}

/**
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */