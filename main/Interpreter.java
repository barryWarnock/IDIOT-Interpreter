package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

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
	protected abstract class Command
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
	}
	/**
	 * ADD takes three numbers, adds the first two and assigns that value to the third
	 */
	protected class ADD extends Command
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
				pane.append("invalid variable passed to ADD \n");
				return null;
			}
			if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
			{
				pane.append("invalid first or second variable passed to ADD \n");
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
	protected class ASSIGN extends Command
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
                pane.append("Nonexistant variable passed to ASSIGN \n");
                return null;
            }
			variables.get(var).setValue(val);
			return this.next;
		}
	}
	/**
	 * BLANK is used to represent empty lines in the linked list of Commands
	 */
	protected class BLANK extends Command
	{
		public Command execute(HashMap<String, Variable> variables, JTextArea pane )
		{
			return this.next;
		}
	}
	/**
	 * DIV takes three numbers, divides the first by the second and assigns that value to the third
	 */
	protected class DIV extends Command
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
				pane.append("invalid variable passed to DIV \n");
				return null;
			}
			if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
			{
				pane.append("invalid first or second variable passed to DIV \n");
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
	protected class ENTER extends Command
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
			int initial = io.getText().lastIndexOf('\n');
			io.setEditable(true);
			//wait until a new newline is detected
			while(initial == (io.getText().lastIndexOf('\n')));
			{
			}
			io.setEditable(false);
			String input = io.getText().substring(initial);
			double val = Double.parseDouble(input);
			variables.get(var).setValue(val);
			return this.next;
		}
	}
	/**
	 * INC adds one to the given variable
	 */
	protected class INC extends Command
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
                pane.append("Nonexistant variable passed to INC \n");
                return null;
            }
            //runtime error if the Variable is not initialized
			if(variables.get(var).isInitialized())
            {
                pane.append("Uninitialized variable passed to INC \n");
                return null;
            }
			variables.get(var).setValue((variables.get(var).getValue() + 1));
			return this.next;
		}
	}
	/**
	 *MUL takes three numbers, multiplies the first two and gives that variable to the third
	 */
	protected class MUL extends Command
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
				pane.append("invalid variable passed to MUL \n");
				return null;
			}
			if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
			{
				pane.append("invalid first or second variable passed to MUL \n");
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
	protected class PRINT extends Command
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
	                pane.append("Nonexistant variable passed to PRINT \n");
	                return null;
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
	protected class SUB extends Command
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
				pane.append("invalid variable passed to SUB \n");
				return null;
			}
			if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
			{
				pane.append("invalid first or second variable passed to SUB \n");
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
	protected class VAR extends Command
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
			variables.put(name, new Variable(name));
			return this.next;
		}
	}
	
	
	//The following commands were not in the handout
	protected class GOTO extends Command
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
					errorAt = "Error at line ";
					errorAt += lineNumber;
					errorAt += ": ";
					if (line.startsWith("START") && !started)
					{
						String[] splitLine = line.split("[ ]", 2);
						//if there is anything after the Command other than whitespace
						if (splitLine.length > 1 && splitLine[1].trim().length() >= 1)
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
					else if (!line.startsWith("START") && !started)
					{
						io.append( errorAt + "IDIOT program must begin with START \n");
						error = true;
					}
					else if (line.startsWith("START") && started)
					{
						io.append(errorAt + "only one START per IDIOT program \n");
						error = true;
					}
					else if (line.startsWith("END"))
					{
						String[] splitLine = line.split("[ ]", 2);
						//if there is anything after the Command other than whitespace
						if (splitLine.length > 1 && splitLine[1].trim().length() >= 1)
						{
							io.append(errorAt + "END contains too many arguments \n");
							error = true;
						}
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
							tail = tail.add(new ADD(splitLine[1], splitLine[2], splitLine[3]), lineNumber);
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
							tail = tail.add(new ASSIGN(splitLine[1], val), lineNumber);
						}
					}
					else if (line.startsWith("CMT"))
					{
						tail = tail.add(new BLANK(), lineNumber);
					}
					else if (line.startsWith("DIV"))
					{
	
						String[] splitLine = line.split("[ ]", 5);
						//if there is anything after the command other than whitespace
						if (splitLine.length > 4 && splitLine[4].trim().length() >= 1)
						{
							io.append(errorAt + "DIV contains too many arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new DIV(splitLine[1], splitLine[2], splitLine[3]), lineNumber);
						}
					}
					else if (line.startsWith("ENTER"))
					{
						String[] splitLine = line.split("[ ]", 3);
						//if there is anything after the command name other than whitespace
						if (splitLine.length > 2 && splitLine[2].trim().length() >= 1)
						{
							io.append(errorAt + "ENTER contains too many arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new ENTER(splitLine[1]), lineNumber);
						}
					}
					else if (line.startsWith("INC"))
					{
	
						String[] splitLine = line.split("[ ]", 3);
						//if there is anything after the command other than whitespace
						if (splitLine.length > 2 && splitLine[2].trim().length() >= 1)
						{
							io.append(errorAt + "INC contains too many arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new INC(splitLine[1]), lineNumber);
						}
					}
					else if (line.startsWith("MUL"))
					{
	
						String[] splitLine = line.split("[ ]", 5);
						//if there is anything after the command other than whitespace
						if (splitLine.length > 4 && splitLine[4].trim().length() >= 1)
						{
							io.append(errorAt + "MUL contains too many arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new MUL(splitLine[1], splitLine[2], splitLine[3]), lineNumber);
						}
					}
					else if (line.startsWith("PRINT"))
					{
						char[] charArray = line.toCharArray();
						String toPrint = "";
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
							}
							if(!closed)
							{
								io.append(errorAt + "no closing paren ')\'");
								error = true;
							}
							else
							{
								String[] splitLine = line.split("[)]", 2);
								if (splitLine.length > 1 && splitLine[1].trim().length() >= 1)
								{
									io.append(errorAt + "PRINT contains too many arguments");
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
							String[] splitLine = line.split("[ ]", 3);
							//if there is anything after the command other than whitespace
							if (splitLine.length > 2 && splitLine[2].trim().length() >= 1)
							{
								io.append(errorAt + "PRINT contains too many arguments \n");
								error = true;
							}
							else
							{
								tail = tail.add(new PRINT(splitLine[1], "variable"), lineNumber);
							}
						}
					}
					else if (line.startsWith("SUB"))
					{
	
						String[] splitLine = line.split("[ ]", 5);
						//if there is anything after the command other than whitespace
						if (splitLine.length > 4 && splitLine[4].trim().length() >= 1)
						{
							io.append(errorAt + "SUB contains too many arguments \n");
							error = true;
						}
						else
						{
							tail = tail.add(new SUB(splitLine[1], splitLine[2], splitLine[3]), lineNumber);
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
							tail = tail.add(new VAR(splitLine[1]), lineNumber);
						}
					}
					
					else if (line.startsWith("GOTO"))
					{
						String[] splitLine = line.split("[ ]", 3);
						//if there is anything after the command other than whitespace
						if (splitLine.length > 2 && splitLine[2].trim().length() >= 1)
						{
							io.append(errorAt + "GOTO contains too many arguments \n");
							error = true;
						}
						else
						{
							int gotoLine = Integer.parseInt(splitLine[1]); 
							tail = tail.add(new GOTO(gotoLine), lineNumber);
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
