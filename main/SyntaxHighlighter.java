package main;
/**
 * 
 * @author Travis
 * This class is used to create styled documents that will change the color of 
 * text input dynamically. 
 */

import java.awt.*;

import javax.swing.text.*;

public class SyntaxHighlighter {

	private static int fontSize = 15;
	/**
	 * 
	 * @param text to find the last char of 
	 * @param index of the test to be searched 
	 * @return index of the last char
	 * This method finds the last char of a given text and returns the index
	 * of that location.
	 */
	private static int findLastNonWordChar (String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

	/**
	 * 
	 * @param text to find the first whitespace of  
	 * @param index of the test to be searched 
	 * @return index of the first whitespace
	 * This method finds the first whitespace of a given text and returns the index
	 * of that location.
	 */
    private static int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }
    
    /**
     * 
     * @return Document that represents what the highlighting profile of our interpreter 
     * should be.
     */
    @SuppressWarnings("serial")
	public static DefaultStyledDocument SyntaxHighlighterProfile (int fontSizeChange)
    {
    	//set a minimum font size to prevent invisible text and errors
    	fontSize += fontSizeChange;
    	if(10>=fontSize)
    		fontSize=10;
    	
    	//the attributes are the styles for the Tokens to be 'highlighted' with.
    	//TODO add more styles for different sets of tokens
    	
    	StyleContext sc = new StyleContext();
        
        // Create the main document style
    	final Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
    		StyleConstants.setForeground(defaultStyle, Color.BLACK);
    		StyleConstants.setFontSize(defaultStyle, fontSize);
    		final AttributeSet defaultAttributes = defaultStyle.copyAttributes();
    		
    	final Style ifStyle = sc.addStyle("IfStyle", defaultStyle);
    		StyleConstants.setForeground(ifStyle, Color.MAGENTA);
    		StyleConstants.setFontSize(ifStyle, fontSize);
    		StyleConstants.setBold(ifStyle, true);
    		final AttributeSet ifAttributes = ifStyle.copyAttributes();
    	
        final Style startStyle = sc.addStyle("StartStyle", defaultStyle);
        	StyleConstants.setForeground(startStyle, new Color(0x008400)); //changes the color
        	StyleConstants.setBold(startStyle, true); //sets the font to bold
        	StyleConstants.setFontSize(startStyle, fontSize);
        	final AttributeSet startAttributes =  startStyle.copyAttributes();
        	
        final Style endStyle = sc.addStyle("EndStyle", defaultStyle);
        	StyleConstants.setForeground(endStyle, Color.RED);
        	StyleConstants.setBold(endStyle, true);
        	StyleConstants.setFontSize(endStyle, fontSize);
        	final AttributeSet endAttributes =  endStyle.copyAttributes();
        	
        final Style commentStyle = sc.addStyle("CommentStyle", defaultStyle);
        	StyleConstants.setForeground(commentStyle, Color.BLUE); //changes the color
        	StyleConstants.setBold(commentStyle, true); //sets the font to bold
        	StyleConstants.setFontSize(commentStyle, fontSize);
        	final AttributeSet commentAttributes =  commentStyle.copyAttributes();
        	
        final Style catchAllStyle = sc.addStyle("CatchAllStyle", defaultStyle);
        	StyleConstants.setForeground(catchAllStyle, Color.ORANGE); //changes the color
        	//StyleConstants.setBold(catchAllStyle, true); //sets the font to bold
        	StyleConstants.setFontSize(catchAllStyle, fontSize);
        	final AttributeSet catchAllAttributes =  catchAllStyle.copyAttributes();
         
        //the styling profile as a styled document
        DefaultStyledDocument doc = new DefaultStyledDocument(sc) {

        	public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
        		super.insertString(offset, str, a);

        		//create tokens of each line
                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) 
                	before = 0;
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                //search the tokens for the specific ones to be highlighted 
                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches("(\\W)*(START)")){
                            setCharacterAttributes(wordL, wordR - wordL, startAttributes, false);
                        }else if(text.substring(wordL, wordR).matches("(\\W)*(END)")){
                        	setCharacterAttributes(wordL, wordR - wordL, endAttributes, false);
                        }else if(text.substring(wordL, wordR).matches("(\\W)*(CMT)")){
                        	setCharacterAttributes(wordL, wordR - wordL, commentAttributes, false);
                        }else if(text.substring(wordL, wordR).matches("(\\W)*(IF|ENDIF)")){
                        	setCharacterAttributes(wordL, wordR - wordL, ifAttributes, false);
                        }else if (text.substring(wordL, wordR).matches("(\\W)*(GOTO|VAR|ASSIGN|ADD|SUB|MUL|DIV|PRINT|ENTER)")){              
                        	setCharacterAttributes(wordL, wordR - wordL, catchAllAttributes, false);
                        }else
                            setCharacterAttributes(wordL, wordR - wordL, defaultAttributes, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            public void remove (int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) 
                	before = 0;
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches("(\\W)*(START)")) {
                    setCharacterAttributes(before, after - before, startAttributes, false);
                }else if (text.substring(before, after).matches("(\\W)*(END)")){
                	setCharacterAttributes(before, after - before, endAttributes, false);
                }else if (text.substring(before, after).matches("(\\W)*(CMT)")){
                	setCharacterAttributes(before, after - before, commentAttributes, false);
                }else if (text.substring(before, after).matches("(\\W)*(IF|ENDIF)")){
                	setCharacterAttributes(before, after - before, ifAttributes, false);
                }else if(text.substring(before, after).matches("(\\W)*(GOTO|VAR|ASSIGN|ADD|SUB|MUL|DIV|PRINT|ENTER)")){
                	setCharacterAttributes(before, after - before, catchAllAttributes, false);
                }else{
                    setCharacterAttributes(before, after - before, defaultAttributes, false);
                }
            }
        }; 
        return doc;
    }
}
