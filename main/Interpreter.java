package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JTextArea;

/**
 * @author Barry Warnock
 * Interpreter handles the syntax highlighting and execution of
 * the IDIOT code, the Variable and Command classes are
 * defined inside of Interpreter because they will not be
 * used anywhere else.
 */
public class Interpreter
{
	protected JTextArea io;

	Interpreter()
	{
		io = null;
	}

	public void setIo(JTextArea io)
	{
		this.io = io;
	}
	
	/**
	 * removes the highlighting from content, checks the code for errors line by line,
	 * turning each line into a Command object, and iterates through the commands
	 * @param content the code to be run
	 * @throws Exception 
	 */
	public void run(String content) throws Exception
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
							io.append(errorAt + "START contains too many arguments \n");
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
						io.append( errorAt + "IDIOT program must begin with START \n");
						error = true;
					}
					else if (tokens[0].equals("START") && started)
					{
						io.append(errorAt + "only one START per IDIOT program \n");
						error = true;
					}
					else if (tokens[0].equals("END"))
					{
						 
						//if there is anything after the Command other than whitespace
						if (tokens.length > 1 && tokens[1].trim().length() >= 1)
						{
							io.append(errorAt + "END contains too many arguments \n");
							error = true;
						}
						ended = true;
					}
					else if (tokens[0].equals("ADD"))
					{
						//if there is anything after the command other than whitespace
						if (tokens.length > 4 && tokens[4].trim().length() >= 1)
						{
							io.append(errorAt + "ADD contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 4)
						{
							io.append(errorAt + "ADD contains too few arguments \n");
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
							io.append(errorAt + "ASSIGN contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 3)
						{
							io.append(errorAt + "ASSIGN contains too few arguments \n");
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
							io.append(errorAt + "DIV contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 4)
						{
							io.append(errorAt + "DIV contains too few arguments \n");
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
							io.append(errorAt + "ENTER contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 2)
						{
							io.append(errorAt + "ENTER contains too few arguments \n");
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
							io.append(errorAt + "INC contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 2)
						{
							io.append(errorAt + "INC contains too few arguments \n");
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
							io.append(errorAt + "MUL contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 4)
						{
							io.append(errorAt + "MUL contains too few arguments \n");
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
							io.append(errorAt + "PRINT contains too few arguments \n");
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
								io.append(errorAt + "no closing paren ')\'");
								error = true;
							}
							else
							{
								if (tokens[tokens.length-1].charAt(tokens[tokens.length-1].length()-1) != ')')
								{
									io.append(errorAt + "PRINT contains too many arguments");
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
								io.append(errorAt + "PRINT contains too many arguments \n");
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
							io.append(errorAt + "SUB contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 4)
						{
							io.append(errorAt + "SUB contains too few arguments \n");
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
							io.append(errorAt + "VAR contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 2)
						{
							io.append(errorAt + "VAR contains too few arguments \n");
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
							io.append(errorAt + "GOTO contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 2)
						{
							io.append(errorAt + "GOTO contains too few arguments \n");
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
							io.append(errorAt + "IF contains too many arguments \n");
							error = true;
						}
						//if there are too few arguments
						if (tokens.length < 4)
						{
							io.append(errorAt + "IF contains too few arguments \n");
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
								io.append(errorAt + "unrecognized condition passed to if \n");
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
								io.append(errorAt + "ENDIF contains too many arguments \n");
								error = true;
							}
							else
							{
								if (IF.IFs.size() == 0)
								{
									io.append(errorAt + "No unclosed IF to close \n");
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
						io.append(errorAt + "Unrecognized keyword \n");
						error = true;
					}
					lineNumber++;
				}
				if (!ended)
				{
					io.append("IDIOT program must end with END \n");
				}
				else if (IF.IFs.size() > 0)
				{
					io.append("error at line " + IF.IFs.get(0).getLine() + " unclosed IF");
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
				io.append("error sending code to interpreter \n");
			}
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				io.append("Failed to close BufferedReader \n");
			}
		}
	}
}

/**
 * Variable is that class that will represent our idiot variables
 */
class Variable
{
	protected String name;
	protected double value;
	protected boolean initialized = false;
	/**
	 * Variables constructor simply gives a Variable a name
	 * @param name the name of the new interpreter
	 */
	Variable(String name)
	{
		this.name = name;
	}
	/**
	 * @return name the Variables name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @return value the current value of the Variable
	 */
	public double getValue()
	{
		return value;
	}
	/**
	 * @param value the new value of the Variable
	 */
	public void setValue(double value)
	{
		this.value = value;
		if (!initialized)
		{
			initialized = true;
		}
	}
	/**
	 * @return initialized true if the variable has been initialized false if it has not
	 */
	public boolean isInitialized()
	{
		return initialized;
	}
	public int hashCode()
	{
		return name.hashCode();
	}
	public boolean equals(Variable other)
	{
		if(this.name == other.name)
		{
			return true;
		}
		return false;
	}
	public String toString()
	{
		if (initialized)
        {
            return value + "\n";
        }
        else
        {
            return (name + " has not been innitialized \n");
        }
	}
}
/**
 * the Command interface will be used by all of the commands so that a list of them can be built and iterated through
 */
abstract class Command
{
	/**
	 * @param variables a list of variables the command needs access to
	 * @param pane the pane that will be used for io
	 * @return Command program will end when execute returns null
	 */
	public abstract Command execute(HashMap<String, Variable> variables, JTextArea pane );
	protected Command previous = null;
	public void setPrevious(Command previous)
	{
		this.previous = previous;
	}
	protected Command next = null;
	protected int lineNumber;
	public void setLine(int line)
	{
		lineNumber = line;
	}
	public Command add(Command next, int lineNumber)
	{
		this.next = next;
		next.setPrevious(this);
		next.setLine(lineNumber);
		return next;
	}
	public int getLine()
	{
		return lineNumber;
	}
	protected void logError(String errMsg, JTextArea pane)
	{
		pane.append("Error at line " + lineNumber +": " + errMsg + "\n");
	}
}
/**
 * ADD takes three numbers, adds the first two and assigns that value to the third
 */
class ADD extends Command
{
	protected String first, second, third;
	/**
	 * @param first the first variable
	 * @param second the second variable
	 * @param third the variable that is to receive the sum of the others
	 */
	ADD(String first, String second, String third)
	{
		this.first = first;
		this.second = second;
		this.third = third;
	}
	/**
	 * adds the first and second variables and assigns that value to the third
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextArea pane )
	{
		/*
		* there will be a runtime error if:
		* any of the Variables has not been created
		* or if the first two don't have values
		*/
		if (variables.get(first) == null || variables.get(second) == null || variables.get(third) == null)
		{
			logError("invalid variable passed to ADD", pane);
			return null;
		}
		if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
		{
			logError("invalid first or second variable passed to ADD", pane);
			return null;
		}
		double f = variables.get(first).getValue();
		double s = variables.get(second).getValue();
		double t = f + s;
		variables.get(third).setValue(t);
		return this.next;
	}
}
/**
 * ASSIGN assigns the given value to the given Variable
 */
class ASSIGN extends Command
{
	protected String var;
	protected double val;
	/**
	 * @param var the name of the variable to be filled
	 * @param val the value to place in var
	 */
	ASSIGN(String var, double val)
	{
		this.var = var;
		this.val = val;
	}
	/**
	 * assigns the given value to the given Variable
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextArea pane )
	{
		//runtime error if the variable does not exist
		if(variables.get(var) == null)
        {
            logError("Nonexistant variable passed to ASSIGN", pane);
            return null;
        }
		variables.get(var).setValue(val);
		return this.next;
	}
}
/**
 * BLANK is used to represent empty lines or comments in the linked list of Commands
 */
class BLANK extends Command
{
	public Command execute(HashMap<String, Variable> variables, JTextArea pane )
	{
		return this.next;
	}
}
/**
 * DIV takes three numbers, divides the first by the second and assigns that value to the third
 */
class DIV extends Command
{
	protected String first, second, third;
	/**
	 * @param first the first variable
	 * @param second the second variable
	 * @param third the variable that is to receive the sum of the others
	 */
	DIV(String first, String second, String third)
	{
		this.first = first;
		this.second = second;
		this.third = third;
	}
	/**
	 * divides the first number by the second and assigns that value to the third
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextArea pane )
	{
		/*
		* there will be a runtime error if:
		* any of the Variables has not been created
		* or if the first two don't have values
		*/
		if (variables.get(first) == null || variables.get(second) == null || variables.get(third) == null)
		{
			logError("invalid variable passed to DIV", pane);
			return null;
		}
		if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
		{
			logError("invalid first or second variable passed to DIV", pane);
			return null;
		}
		double f = variables.get(first).getValue();
		double s = variables.get(second).getValue();
		double t = f / s;
		variables.get(third).setValue(t);
		return this.next;
	}
}
/**
 * ENTER takes input from the user and assigns it to the given Variable
 */
class ENTER extends Command
{
	protected String var;
	/**
	 * @param var the variable to fill with the user input value
	 */
	ENTER(String var)
	{
		this.var = var;
	}
	/**
	 * will somehow take input from the user and put it in the given Variable
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextArea pane )
	{
		//runtime error if the variable does not exist
		if (variables.get(var) == null)
		{
			logError("invalid variable passed to ENTER", pane);
		}
		int initial = pane.getText().lastIndexOf('\n');
		if (initial == -1)
		{
			initial = 0;
		}
		pane.setEditable(true);
		//wait until a new newline is detected
		if (pane.getText().lastIndexOf('\n') == -1)
		{
			while (pane.getText().lastIndexOf('\n') == -1)
			{
			}
		}
		else
		{
			while(initial == (pane.getText().lastIndexOf('\n')));
			{
			}
		}
		pane.setEditable(false);
		String input = pane.getText().substring(initial, pane.getText().lastIndexOf('\n'));
		double val = 0;
		try
		{
			val = Double.parseDouble(input);
		}
		catch (java.lang.NumberFormatException e)
		{
			logError("Input was not a number", pane);
			return null;
		}
		variables.get(var).setValue(val);
		return this.next;
	}
}
/**
 * INC adds one to the given variable
 */
class INC extends Command
{
	protected String var;
	/**
	 * @param var the variable to add one to
	 */
	INC(String var)
	{
		this.var = var;
	}
	/**
	 * adds one to the given Variable
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextArea pane )
	{
		//runtime error if the Variable does not exist
		if(variables.get(var) == null)
        {
			logError("Nonexistant variable passed to INC", pane);
            return null;
        }
        //runtime error if the Variable is not initialized
		if(!variables.get(var).isInitialized())
        {
			logError("Uninitialized variable passed to INC", pane);
            return null;
        }
		variables.get(var).setValue((variables.get(var).getValue() + 1));
		return this.next;
	}
}
/**
 *MUL takes three numbers, multiplies the first two and gives that variable to the third
 */
class MUL extends Command
{
	protected String first, second, third;
	/**
	 * @param first
	 * @param second
	 * @param third
	 */
	MUL(String first, String second, String third)
	{
		this.first = first;
		this.second = second;
		this.third = third;
	}
	/**
	 * multiplies the first and second numbers and gives that value to the third
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextArea pane )
	{
		/*
		* there will be a runtime error if:
		* any of the Variables has not been created
		* or if the first two don't have values
		*/
		if (variables.get(first) == null || variables.get(second) == null || variables.get(third) == null)
		{
			logError("invalid variable passed to MUL", pane);
			return null;
		}
		if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
		{
			logError("invalid first or second variable passed to MUL", pane);
			return null;
		}
		double f = variables.get(first).getValue();
		double s = variables.get(second).getValue();
		double t = f * s;
		variables.get(third).setValue(t);
		return this.next;
	}
}
/**
 * PRINT takes a variable or string and outputs its value
 */
class PRINT extends Command
{
	protected String val;
	protected boolean isVar = false;
	protected boolean isString = false;
	/**
	 * takes the value to be output and its type
	 * @param val the Variable or string to output
	 * @param type 'string' if the value is a string or 'variable' if it is a variable
	 */
	PRINT(String val, String type)
	{
		if (type == "variable")
		{
			isVar = true;
		}
		else if (type == "string")
		{
			isString = true;
		}
		this.val = val;
	}
	/**
	 * checks what type the value it then prints it
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextArea pane )
	{
		if (isVar)
		{
			//runtime error if variable does not exist
			if(variables.get(val) == null)
            {
				logError("Nonexistant variable passed to PRINT", pane);
                return null;
            }
			//runtime error if variable is not initialized
			if (variables.get(val).isInitialized() == false)
			{
				logError("Uninitialized variable passed to PRINT", pane);
			}
			pane.append(variables.get(val).toString());
		}
		else if (isString)
		{
			pane.append(val + "\n");
		}
		else
		{
			return null;
		}
		return this.next;
	}
}
/**
 * SUB takes three values subtracts the second from the first then assigns that value to the third
 */
class SUB extends Command
{
	protected String first, second, third;
	/**
	 * @param first
	 * @param second
	 * @param third
	 */
	SUB(String first, String second, String third)
	{
		this.first = first;
		this.second = second;
		this.third = third;
	}
	/**
	 * divides the first number by the second and assigns that value to the third
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextArea pane )
	{
		/*
		* there will be a runtime error if:
		* any of the Variables has not been created
		* or if the first two don't have values
		*/
		if (variables.get(first) == null || variables.get(second) == null || variables.get(third) == null)
		{
			logError("invalid variable passed to SUB", pane);
			return null;
		}
		if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
		{
			logError("invalid first or second variable passed to SUB", pane);
			return null;
		}
		double f = variables.get(first).getValue();
		double s = variables.get(second).getValue();
		double t = f - s;
		variables.get(third).setValue(t);
		return this.next;
	}
}
/**
 * VAR creates a Variable and adds it to the HashMap
 */
class VAR extends Command
{
	protected String name;
	/**
	 * @param name
	 */
	VAR(String name)
	{
		this.name = name;
	}
	/**
	 * creates a new Variable with the given name and adds it to the HashMap
	 */
	public Command execute(HashMap<String, Variable> variables, JTextArea pane )
	{
		//runtime error if the variable name includes '(' or ')'
		if (name.contains("(") || name.contains(")"))
		{
			logError("Variable name contains illegal characters", pane);
			return null;
		}
		//runtime error if a variable with this name already exists
		if (variables.get(name) != null)
		{
			logError("Variable with that name already exists", pane);
			return null;
		}
		variables.put(name, new Variable(name));
		return this.next;
	}
}


//The following commands were not in the handout
class GOTO extends Command
{
	int line;
	GOTO(int line)
	{
		this.line = line;
	}
	public Command execute(HashMap<String, Variable> variables, JTextArea pane) 
	{
		Command toReturn = this;
		boolean searching = true;
		while (searching)
		{
			if (toReturn == null)
			{
				searching = false;
				logError("GOTO was given an invalid line", pane);
			}
			else if (line > toReturn.getLine())
			{
				toReturn = toReturn.next;
			}
			else if (line < toReturn.getLine())
			{
				toReturn = toReturn.previous;
			}
			else if (line == toReturn.getLine())
			{
				searching = false;
			}
		}
		return toReturn;
	}
}
class IF extends Command
{
	//link to the closing ENDIF
	public Command elseIF = null;
	//contains a static list of unclosed IFs
	public static ArrayList<IF> IFs = new ArrayList<IF>();
	String var, cond;
	double val;
	//variable, condition, value
	IF(String variable,String condition, double value)
	{
		var = variable;
		cond = condition;
		val = value;
		IFs.add(this);
	}
	//returns the next command if true otherwise returns else
	public Command execute(HashMap<String, Variable> variables, JTextArea pane) 
	{
		//runtime error if the Variable does not exist
		if(variables.get(var) == null)
		{
			logError("Nonexistant variable passed to IF", pane);
		    return null;
		}
		//runtime error if the Variable is not initialized
		if(!variables.get(var).isInitialized())
		{
			logError("Uninitialized variable passed to IF", pane);
		return null;
		}
		Command toReturn = elseIF;
		if (cond == "equals")
		{
			if (variables.get(var).getValue() == val)
			{
				toReturn = next;
			}
		}
		if (cond == "greater")
		{
			if (variables.get(var).getValue() > val)
			{
				toReturn = next;
			}
		}
		if (cond == "less")
		{
			if (variables.get(var).getValue() < val)
			{
				toReturn = next;
			}
		}
		if (cond == "not")
		{
			if (variables.get(var).getValue() != val)
			{
				toReturn = next;
			}
		}
		return toReturn;
	}
}

class ENDIF extends Command
{
	ENDIF()
	{
		IF.IFs.remove(IF.IFs.size()-1).elseIF = this;
	}
	public Command execute(HashMap<String, Variable> variables, JTextArea pane) 
	{
		return next;
	}
}
