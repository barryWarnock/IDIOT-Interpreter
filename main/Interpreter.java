package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * @author Barry Warnock
 * Interpreter handles the syntax highlighting and execution of
 * the IDIOT code, the Variable and Command classes are
 * defined inside of Interpreter because they will not be
 * used anywhere else.
 */
public class Interpreter
{
	static protected JTextArea io;

	Interpreter()
	{
		io = null;
	}

	public void setIo(JTextArea io)
	{
		Interpreter.io = io;
		io.setEditable(false);
	}
	
	static public void logToIo(final String message)
	{
		SwingUtilities.invokeLater(new Runnable() 
		 {
			    public void run() 
			    {
			    	io.append(message);
			    }
		 });
	}
	
	/**
	 * removes the highlighting from content, checks the code for errors line by line,
	 * turning each line into a Command object, and iterates through the commands
	 * @param content the code to be run
	 * @throws Exception 
	 */
	static public void run(String content) throws Exception
	{
		if (io == null)
		{
			throw new Exception("No io Text Area set");
		}
		else
		{
			//clear the console before running
			io.setText(null);
			io.setEditable(false);
			HashMap<String, Variable> variables = new HashMap<String, Variable>();
			Command head = null;
			Command tail = null;
			BufferedReader reader = new BufferedReader(new StringReader(content));
			String line = null;
			boolean started = false;
			boolean ended = false;
			boolean error = false;
			int lineNumber = 1;
			String errorAt;
			try
			{
				while(((line=reader.readLine()) != null) && !ended)
				{
					line = line.trim();
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
								Interpreter.logToIo(errorAt + "no closing paren ')\'");
								error = true;
							}
							else
							{
								if (tokens[tokens.length-1].charAt(tokens[tokens.length-1].length()-1) != ')')
								{
									Interpreter.logToIo(errorAt + "PRINT contains too many arguments");
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
				if (!ended)
				{
					Interpreter.logToIo("IDIOT program must end with END \n");	
				}
				else if (IF.IFs.size() > 0)
				{
					Interpreter.logToIo("error at line " + IF.IFs.get(0).getLine() + " unclosed IF");
				}
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
