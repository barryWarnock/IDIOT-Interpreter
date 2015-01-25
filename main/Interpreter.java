package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;
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
	/**
	 * Variable is that class that will represent our idiot variables
	 */
	protected class Variable
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
			return value + "\n";
		}
	}
	/**
	 * the Command interface will be used by all of the commands so that a list of them can be built and iterated through
	 */
	protected interface Command
	{
		/**
		 * @param variables a list of variables the command needs access to
		 * @param pane the pane that will be used for io
		 * @return boolean if there is an error execute will write it to the pane and return false
		 */
		public boolean execute(HashMap<String, Variable> variables, JTextArea pane );
	}
	/**
	 * ADD takes three numbers, adds the first two and assigns that value to the third
	 */
	protected class ADD implements Command
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
		public boolean execute(HashMap<String, Variable> variables, JTextArea pane )
		{
			/*
			* there will be a runtime error if:
			* any of the Variables has not been created
			* or if the first two don't have values
			*/
			if (variables.get("first") == null || variables.get("second") == null || variables.get("third") == null)
			{
				//print "invalid variable passed to ADD command"
				return false;
			}
			if (!variables.get("first").isInitialized() || !variables.get("second").isInitialized())
			{
				//print "uninitialized first or second variable passed to ADD command"
				return false;
			}
			double f = variables.get(first).getValue();
			double s = variables.get(second).getValue();
			double t = f + s;
			variables.get(third).setValue(t);
			return true;
		}
	}
	/**
	 * ASSIGN assigns the given value to the given Variable
	 */
	protected class ASSIGN implements Command
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
		public boolean execute(HashMap<String, Variable> variables, JTextArea pane )
		{
			variables.get(var).setValue(val);
			return true;
		}
	}
	/**
	 * DIV takes three numbers, divides the first by the second and assigns that value to the third
	 */
	protected class DIV implements Command
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
		public boolean execute(HashMap<String, Variable> variables, JTextArea pane )
		{
			double f = variables.get(first).getValue();
			double s = variables.get(second).getValue();
			double t = f / s;
			variables.get(third).setValue(t);
			return true;
		}
	}
	/**
	 * ENTER takes input from the user and assigns it to the given Variable
	 */
	protected class ENTER implements Command
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
		public boolean execute(HashMap<String, Variable> variables, JTextArea pane )
		{
			//variables.get(var).setValue(something);
			return true;
		}
	}
	/**
	 * INC adds one to the given variable
	 */
	protected class INC implements Command
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
		public boolean execute(HashMap<String, Variable> variables, JTextArea pane )
		{
			variables.get(var).setValue((variables.get(var).getValue() + 1));
			return true;
		}
	}
	/**
	 *MUL takes three numbers, multiplies the first two and gives that variable to the third
	 */
	protected class MUL implements Command
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
		public boolean execute(HashMap<String, Variable> variables, JTextArea pane )
		{
			double f = variables.get(first).getValue();
			double s = variables.get(second).getValue();
			double t = f * s;
			variables.get(third).setValue(t);
			return true;
		}
	}
	/**
	 * PRINT takes a variable or string and outputs its value
	 */
	protected class PRINT implements Command
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
		public boolean execute(HashMap<String, Variable> variables, JTextArea pane )
		{
			if (isVar)
			{
				pane.append(variables.get(val).toString());
			}
			else if (isString)
			{
				pane.append(val + "\n");
			}
			else
			{
				return false;
			}
			return true;
		}
	}
	/**
	 * SUB takes three values subtracts the second from the first then assigns that value to the third
	 */
	protected class SUB implements Command
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
		public boolean execute(HashMap<String, Variable> variables, JTextArea pane )
		{
			double f = variables.get(first).getValue();
			double s = variables.get(second).getValue();
			double t = f - s;
			variables.get(third).setValue(t);
			return true;
		}
	}
	/**
	 * VAR creates a Variable and adds it to the HashMap
	 */
	protected class VAR implements Command
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
		public boolean execute(HashMap<String, Variable> variables, JTextArea pane )
		{
			variables.put(name, new Variable(name));
			return true;
		}
	}
	
	protected JTextArea io;
	
	/**
	 * takes just one parameter, a reference to the pane that is to be used for i/o
	 * @param io the pane to be used for i/o
	 */
	Interpreter(JTextArea io)
	{
		this.io = io;
	}
	/**
	 * takes the content of the code editor pane, formats it, and returns the formatted text to the pane
	 * @param content the current content of the pane used for code editing
	 * @return content the highlighted code
	 */
	public String highlight(String content)
	{
		//do stuff to content
		//http://stackoverflow.com/questions/14400946/how-to-change-the-color-of-specific-words-in-a-jtextpane
		return content;
	}
	/**
	 * removes the highlighting from content, checks the code for errors line by line, 
	 * turning each line into a Command object, and iterates through the commands
	 * @param content the code to be run
	 */
	public void run(String content)
	{
		HashMap<String, Variable> variables = new HashMap<String, Variable>();
		ArrayList<Command> commands = new ArrayList<Command>();
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
				errorAt = "Error at line ";
				errorAt += lineNumber;
				errorAt += ": ";
				if (line.equals("START") && !started)
				{
					started = true;
				}
				else if (!line.equals("START") && !started)
				{
					io.append( errorAt + "IDIOT program must begin with START \n");
					error = true;
				}
				else if (line.equals("START") && started)
				{
					io.append(errorAt + "only one START per IDIOT program \n");
					error = true;
				}
				else if (line.equals("END"))
				{
					ended = true;
				}
				else if (line.startsWith("ADD"))
				{
					String[] splitLine = line.split("[ ]", 5);
					//if there is anything after the command other than whitespace
					if (splitLine.length > 4 && splitLine[4].trim().length() >= 1)
					{
						io.append(errorAt + "ADD contains too many arguments \n");
						error = true;
					}
					else
					{
						commands.add(new ADD(splitLine[1], splitLine[2], splitLine[3]));
					}
				}
				else if (line.startsWith("ASSIGN"))
				{
					String[] splitLine = line.split("[ ]", 4);
					//if there is anything after the command other than whitespace
					if (splitLine.length > 3 && splitLine[3].trim().length() >= 1)
					{
						io.append(errorAt + "ASSIGN contains too many arguments \n");
						error = true;
					}
					else
					{
						double val = Double.parseDouble(splitLine[2]);
						commands.add(new ASSIGN(splitLine[1], val));
					}
				}
				else if (line.startsWith("CMT"))
				{
				}
				//print only prints variables right now
				else if (line.startsWith("PRINT"))
				{
					char[] charArray = line.toCharArray();
					String toPrint = "";
					int endOfString = 8;
					if (charArray[6] == '(')
					{
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
							endOfString++;
						}
						if(!closed)
						{
							io.append(errorAt + "no closing paren ')\'");
							error = true;
						}
						else
						{
							//run through the rest of the charArray to ensure its all whitespace
							boolean nonWhitespace = false;
							for (int i = endOfString; i < charArray.length && !nonWhitespace; i++)
							{
								if (charArray[i] == ' ')
								{
									nonWhitespace = true;
									io.append(errorAt + "PRINT has too many arguments");
									error = true;
								}
							}
							if (!nonWhitespace)
							{
								commands.add(new PRINT(toPrint, "string"));
							}
						}
					}
					//if variable
					else
					{
						String[] splitLine = line.split("[ ]", 3);
						//if there is anything after the command other than whitespace
						if (splitLine.length > 2 && splitLine[2].trim().length() >= 1)
						{
							io.append(errorAt + "PRINT contains too many arguments \n");
							error = true;
						}
						else
						{
							commands.add(new PRINT(splitLine[1], "variable"));
						}
					}
				}
				else if (line.startsWith("VAR"))
				{
					String[] splitLine = line.split("[ ]", 3);
					//if there is anything after the variable name other than whitespace
					if (splitLine.length > 2 && splitLine[2].trim().length() >= 1)
					{
						io.append(errorAt + "VAR contains too many arguments \n");
						error = true;
					}
					else
					{
						commands.add(new VAR(splitLine[1]));
					}
					}
				//a blank line
				else if (line.trim().length() < 1)
				{
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
			else
			{
				if(!error)
				{
					for (Command command : commands)
					{
						command.execute(variables, io);
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
