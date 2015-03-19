package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import actions.EnterAction;

/**
 * @author Barry Warnock
 * Interpreter takes the text from the gui and runs it as an idiot program
 */
public class Interpreter
{
	static protected JTextPane io;

	/**
	 * The interpreter constructor does nothing but explicitly set the
	 * JTextArea used for i/o to null
	 */
	Interpreter()
	{
		io = null;
	}

	/**
	 * setIo sets the JTextArea to be used for i/o and adds the keybindings to it that are used for ENTER
	 * @param io the JTextArea to use for i/o
	 */
	public void setIo(JTextPane io)
	{
		Interpreter.io = io;
		io.setEditable(false);
		
		io.getInputMap().put(KeyStroke.getKeyStroke("0"), "zero");
		io.getActionMap().put("zero", new EnterAction("0"));
		io.getInputMap().put(KeyStroke.getKeyStroke("1"), "one");
		io.getActionMap().put("one", new EnterAction("1"));
		io.getInputMap().put(KeyStroke.getKeyStroke("2"), "two");
		io.getActionMap().put("two", new EnterAction("2"));
		io.getInputMap().put(KeyStroke.getKeyStroke("3"), "three");
		io.getActionMap().put("three", new EnterAction("3"));
		io.getInputMap().put(KeyStroke.getKeyStroke("4"), "four");
		io.getActionMap().put("four", new EnterAction("4"));
		io.getInputMap().put(KeyStroke.getKeyStroke("5"), "five");
		io.getActionMap().put("five", new EnterAction("5"));
		io.getInputMap().put(KeyStroke.getKeyStroke("6"), "six");
		io.getActionMap().put("six", new EnterAction("6"));
		io.getInputMap().put(KeyStroke.getKeyStroke("7"), "seven");
		io.getActionMap().put("seven", new EnterAction("7"));
		io.getInputMap().put(KeyStroke.getKeyStroke("8"), "eight");
		io.getActionMap().put("eight", new EnterAction("8"));
		io.getInputMap().put(KeyStroke.getKeyStroke("9"), "nine");
		io.getActionMap().put("nine", new EnterAction("9"));
		
		io.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enter");
		io.getActionMap().put("enter", new EnterAction("\n"));
		io.getInputMap().put(KeyStroke.getKeyStroke("PERIOD"), "point");
		io.getActionMap().put("point", new EnterAction("."));
	}
	
	/**
	 * all calls to append are called through this method which uses SwingUtilities.invokeLater
	 * to ensure that appending to a JTextArea in another thread doesn't lock up the GUI
	 * @param message the text to be printed to the i/o console
	 */
	static public void logToIo(final String message)
	{
		SwingUtilities.invokeLater(new Runnable() 
		 {
			    public void run() 
			    {
			    	try {
						io.getDocument().insertString(io.getDocument().getLength(), message, null);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
		 });
	}
	
	/**
	 * run goes through the text from the gui line by line, turning each line into a
	 * Command object and checking for errors along the way.
	 * <p>
	 * if no errors are found then the linked list on Commands is run though until they 
	 * have all been executed
	 * @param content the IDIOT code to be interpreted
	 * @throws Exception run throws an exception if no JTextArea has been set for i/o yet
	 */
	public void run(String content) throws Exception
	{
		if (io == null)
		{
			throw new Exception("No io Text Area set");
		}
		else
		{
			//prepare the console before running
			io.setText(null);
			io.setEditable(false);
			//the HashMap that will store IDIOT variables
			HashMap<String, Variable> variables = new HashMap<String, Variable>();
			//the head of our linked list of Commands along with a tail to point to the most current Command
			Command head = null;
			Command tail = null;
			BufferedReader reader = new BufferedReader(new StringReader(content));
			String line = null;
			//tracks the various document wide conditions that the interpreter cares about
			boolean started = false;
			boolean ended = false;
			boolean error = false;
			//tracks the current line number
			int lineNumber = 1;
			//this string will be updated every line to make printing error messages easier
			String errorAt;
			try
			{
				//while there are unread lines in the code
				while(((line=reader.readLine()) != null) && !ended)
				{
					//remove whitespace before and after the line
					line = line.trim();
					//split up each whitespace seperated word into its own string, no Command should have more than 4 so the 5'th
					//is for error checking
					String[] tokens = line.split(" ", 5);
					errorAt = "Error at line ";
					errorAt += lineNumber;
					errorAt += ": ";
					if (tokens[0].equals("START") && !started)
					{
						//if there is anything after the Command other than whitespace
						if (tokens.length > 1 && tokens[1].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "START contains too many arguments \n");
							error = true;
						}
						else
						{
							started = true;
							head = new BLANK();
							head.setLine(lineNumber);
							tail = head;
						}
					}
					else if (!tokens[0].equals("START") && !started)
					{
						Interpreter.logToIo( errorAt + "IDIOT program must begin with START \n");
						error = true;
					}
					else if (tokens[0].equals("START") && started)
					{
						Interpreter.logToIo(errorAt + "only one START per IDIOT program \n");
						error = true;
					}
					else if (tokens[0].equals("END"))
					{
						 
						//if there is anything after the Command other than whitespace
						if (tokens.length > 1 && tokens[1].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "END contains too many arguments \n");
							error = true;
						}
						ended = true;
						tail = tail.add(new BLANK(), lineNumber);
					}
					else if (tokens[0].equals("ADD"))
					{
						//if there is anything after the command other than whitespace
						if (tokens.length > 4 && tokens[4].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "ADD contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 4)
						{
							Interpreter.logToIo(errorAt + "ADD contains too few arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new ADD(tokens[1], tokens[2], tokens[3]), lineNumber);
						}
					}
					else if (tokens[0].equals("ASSIGN"))
					{
						//if there is anything after the command other than whitespace
						if (tokens.length > 3 && tokens[3].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "ASSIGN contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 3)
						{
							Interpreter.logToIo(errorAt + "ASSIGN contains too few arguments \n");
							error = true;
						}
						else
						{
							double val = Double.parseDouble(tokens[2]);
							tail = tail.add(new ASSIGN(tokens[1], val), lineNumber);
						}
					}
					else if (tokens[0].equals("CMT"))
					{
						tail = tail.add(new BLANK(), lineNumber);
					}
					else if (tokens[0].equals("DIV"))
					{
						//if there is anything after the command other than whitespace
						if (tokens.length > 4 && tokens[4].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "DIV contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 4)
						{
							Interpreter.logToIo(errorAt + "DIV contains too few arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new DIV(tokens[1], tokens[2], tokens[3]), lineNumber);
						}
					}
					else if (tokens[0].equals("ENTER"))
					{
						//if there is anything after the command name other than whitespace
						if (tokens.length > 2 && tokens[2].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "ENTER contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 2)
						{
							Interpreter.logToIo(errorAt + "ENTER contains too few arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new ENTER(tokens[1]), lineNumber);
						}
					}
					else if (tokens[0].equals("INC"))
					{
						//if there is anything after the command other than whitespace
						if (tokens.length > 2 && tokens[2].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "INC contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 2)
						{
							Interpreter.logToIo(errorAt + "INC contains too few arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new INC(tokens[1]), lineNumber);
						}
					}
					else if (tokens[0].equals("MUL"))
					{
						//if there is anything after the command other than whitespace
						if (tokens.length > 4 && tokens[4].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "MUL contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 4)
						{
							Interpreter.logToIo(errorAt + "MUL contains too few arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new MUL(tokens[1], tokens[2], tokens[3]), lineNumber);
						}
					}
					else if (tokens[0].equals("PRINT"))
					{
						//if there are too few arguments
						if (tokens.length < 2)
						{
							Interpreter.logToIo(errorAt + "PRINT contains too few arguments \n");
							error = true;
						}
						String toPrint = "";
						if (tokens[1].charAt(0) == '(')
						{
							char[] charArray = line.toCharArray();
							boolean closed = false;
							for (int i = 7; i < charArray.length && !closed; i++)
							{
								if (charArray[i] != ')')
								{
									toPrint += charArray[i];
								}
								else
								{
									closed = true;
								}
							}
							if(!closed)
							{
								Interpreter.logToIo(errorAt + "no closing paren ')' \n");
								error = true;
							}
							else
							{
								if (tokens[tokens.length-1].charAt(tokens[tokens.length-1].length()-1) != ')')
								{
									Interpreter.logToIo(errorAt + "PRINT contains too many arguments \n");
									error = true;
								}
								else
								{
									tail = tail.add(new PRINT(toPrint, "string"), lineNumber);
								}
							}
						}
						//if variable
						else
						{
							//if there is anything after the command other than whitespace
							if (tokens.length > 2 && tokens[2].trim().length() >= 1)
							{
								Interpreter.logToIo(errorAt + "PRINT contains too many arguments \n");
								error = true;
							}
							else
							{
								tail = tail.add(new PRINT(tokens[1], "variable"), lineNumber);
							}
						}
					}
					else if (tokens[0].equals("SUB"))
					{
						//if there is anything after the command other than whitespace
						if (tokens.length > 4 && tokens[4].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "SUB contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 4)
						{
							Interpreter.logToIo(errorAt + "SUB contains too few arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new SUB(tokens[1], tokens[2], tokens[3]), lineNumber);
						}
					}
					else if (tokens[0].equals("VAR"))
					{
						//if there is anything after the variable name other than whitespace
						if (tokens.length > 2 && tokens[2].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "VAR contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 2)
						{
							Interpreter.logToIo(errorAt + "VAR contains too few arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new VAR(tokens[1]), lineNumber);
						}
					}
					
					else if (tokens[0].equals("GOTO"))
					{
						//if there is anything after the command other than whitespace
						if (tokens.length > 2 && tokens[2].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "GOTO contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 2)
						{
							Interpreter.logToIo(errorAt + "GOTO contains too few arguments \n");
							error = true;
						}
						else
						{
							int gotoLine = Integer.parseInt(tokens[1]); 
							tail = tail.add(new GOTO(gotoLine), lineNumber);
						}
					}
					else if (tokens[0].equals("IF"))
					{
						//if there is anything after the command other than whitespace
						if (tokens.length > 4 && tokens[4].trim().length() >= 1)
						{
							Interpreter.logToIo(errorAt + "IF contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 4)
						{
							Interpreter.logToIo(errorAt + "IF contains too few arguments \n");
							error = true;
						}
						else
						{
							String condition = null;
							if (tokens[2].equals("="))
							{
								condition = "equals";
							}
							else if (tokens[2].equals(">"))
							{
								condition = "greater";
							}
							else if (tokens[2].equals("<"))
							{
								condition = "less";
							}
							else if (tokens[2].equals("!"))
							{
								condition = "not";
							}
							else 
							{
								Interpreter.logToIo(errorAt + "unrecognized condition passed to if \n");
								error = true;
							}
							double val = Double.parseDouble(tokens[3]);
							tail = tail.add(new IF(tokens[1], condition, val), lineNumber);
						}
					}
						else if (tokens[0].equals("ENDIF"))
						{
							
							//if there is anything after the command other than whitespace
							if (tokens.length > 1 && tokens[1].trim().length() >= 1)
							{
								Interpreter.logToIo(errorAt + "ENDIF contains too many arguments \n");
								error = true;
							}
							else
							{
								if (IF.IFs.size() == 0)
								{
									Interpreter.logToIo(errorAt + "No unclosed IF to close \n");
								}
								else
								{
									tail = tail.add(new ENDIF(), lineNumber);
								}
							}
						}
					
					//a blank line
					else if (line.trim().length() < 1)
					{
						tail = tail.add(new BLANK(), lineNumber);
					}
					else
					{
						Interpreter.logToIo(errorAt + "Unrecognized keyword \n");
						error = true;
					}
					lineNumber++;
				}
				//if the program never ended
				if (!ended)
				{
					Interpreter.logToIo("IDIOT program must end with END \n");	
				}
				//if there are still unclosed IFs
				else if (IF.IFs.size() > 0)
				{
					Interpreter.logToIo("error at line " + IF.IFs.get(0).getLine() + " unclosed IF");
				}
				//if the program was ended and had no unclosed IFs then iterate through each one
				else
				{
					Command current = head;
					if(!error)
					{
						while(current != null)
						{
							current = current.execute(variables, io);
						}
					}
				}
			}
			catch (IOException e)
			{
				Interpreter.logToIo("error sending code to interpreter \n");
			}
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				Interpreter.logToIo("Failed to close BufferedReader \n");
			}
		}
	}
}
