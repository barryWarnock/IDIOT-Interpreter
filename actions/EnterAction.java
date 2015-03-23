package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import main.Interpreter;

@SuppressWarnings("serial")
public class EnterAction extends AbstractAction
{
	private String digit;
	//used to detect when the user has backspaced to where they began
	private static int digitsEntered = 0;
	private static boolean hasTyped = false;
	private static boolean editable = false;
	private static boolean lineIsTerminated = false;
	private static String input = "";
	public EnterAction(String digit)
	{
		this.digit = digit;
	}
	public void actionPerformed(ActionEvent e)
	{
		if (!editable)
		{
		}
		else if (digit == "\n")
		{
			lineIsTerminated = true;
		}
		else if (digit == "back")
		{
			 if (input.length() > 0) 
			 {
				 input = input.substring(0, input.length()-1);
			 }
			 String ioText = Interpreter.getIoText();
			 if (digitsEntered > 0) 
			 {
				 ioText = ioText.substring(0, ioText.length()-1);
				 digitsEntered--;
			 }
			 Interpreter.clearIo();
			 Interpreter.logToIo(ioText);
		}
		else
		{
			Interpreter.logToIo(digit);
			input += digit;
			digitsEntered++;
		}
	}
	public static void startListening()
	{
		editable = true;
	}
	/**
	 * returns the input if it is ready and resets the static fields of EnterAction
	 * @return null if the line has not been terminated yet, otherwise the input is returned
	 */
	public static String getInput()
	{
		if(!lineIsTerminated)
		{
			return null;
		}
		else
		{
			lineIsTerminated = false;
			String toReturn = input;
			input = "";
			editable = false;
			digitsEntered = 0;
			return toReturn;
		}
	}
}