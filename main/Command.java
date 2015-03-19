package main;

import actions.EnterAction;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextPane;


/**
 * all Commands will extend Command so that a linked list of Commands can be built and iterated through, each Command will do 
 * all its work in the execute method
 */
abstract class Command
{
	protected Command previous = null;
	protected Command next = null;
	protected int lineNumber;
	/**
	 * @param variables a HashMap containing all of the Variables in the program
	 * @param io the JTextPane to be used for i/o
	 * @return the next command in the list
	 */
	public abstract Command execute(HashMap<String, Variable> variables, JTextPane io );
	/**
	 * @param previous the Command that came before this one in the list
	 */
	public void setPrevious(Command previous)
	{
		this.previous = previous;
	}
	/**
	 * @param line the line this Command appears on in the document
	 */
	public void setLine(int line)
	{
		lineNumber = line;
	}
	/**
	 * attaches a new Command to this one setting its line number as well as setting its previous to this one
	 * @param next the Command to add to the list
	 * @param lineNumber the line the next Command is found on
	 * @return the new end of the list
	 */
	public Command add(Command next, int lineNumber)
	{
		this.next = next;
		next.setPrevious(this);
		next.setLine(lineNumber);
		return next;
	}
	/**
	 * @return the line number of the current Command
	 */
	public int getLine()
	{
		return lineNumber;
	}
	/**
	 * takes an error message, adds line information and a newline, and prints it 
	 * @param errMsg the error to print
	 */
	protected void logError(String errMsg)
	{
		Interpreter.logToIo("Error at line " + lineNumber +": " + errMsg + "\n");
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
	public Command execute(HashMap<String, Variable> variables, JTextPane io )
	{
		/*
		* there will be a runtime error if:
		* any of the Variables has not been created
		* or if the first two don't have values
		*/
		if (variables.get(first) == null || variables.get(second) == null || variables.get(third) == null)
		{
			logError("invalid variable passed to ADD");
			return null;
		}
		if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
		{
			logError("invalid first or second variable passed to ADD");
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
	 * @param var the name of the Variable to be filled
	 * @param val the value to assign the Variable to be filled
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
	public Command execute(HashMap<String, Variable> variables, JTextPane io )
	{
		//runtime error if the variable does not exist
		if(variables.get(var) == null)
        {
            logError("Nonexistant variable passed to ASSIGN");
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
	/**
	 * simply returns the next Command in the list
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextPane io )
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
	public Command execute(HashMap<String, Variable> variables, JTextPane io )
	{
		/*
		* there will be a runtime error if:
		* any of the Variables has not been created
		* or if the first two don't have values
		*/
		if (variables.get(first) == null || variables.get(second) == null || variables.get(third) == null)
		{
			logError("invalid variable passed to DIV");
			return null;
		}
		if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
		{
			logError("invalid first or second variable passed to DIV");
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
	 * takes input from the user and assigns it to the assigned Variable
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextPane io)
	{
		//runtime error if the variable does not exist
		if (variables.get(var) == null)
		{
			logError("invalid variable passed to ENTER");
		}
		String input = null;
		while (input == null)
		{
			input = EnterAction.getInput();
		}
		double val = 0;
		Interpreter.logToIo("\n");
		try
		{
			val = Double.parseDouble(input);
		}
		catch (java.lang.NumberFormatException e)
		{
			logError("Input was not a number");
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
	public Command execute(HashMap<String, Variable> variables, JTextPane io )
	{
		//runtime error if the Variable does not exist
		if(variables.get(var) == null)
        {
			logError("Nonexistant variable passed to INC");
            return null;
        }
        //runtime error if the Variable is not initialized
		if(!variables.get(var).isInitialized())
        {
			logError("Uninitialized variable passed to INC");
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
	public Command execute(HashMap<String, Variable> variables, JTextPane io )
	{
		/*
		* there will be a runtime error if:
		* any of the Variables has not been created
		* or if the first two don't have values
		*/
		if (variables.get(first) == null || variables.get(second) == null || variables.get(third) == null)
		{
			logError("invalid variable passed to MUL");
			return null;
		}
		if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
		{
			logError("invalid first or second variable passed to MUL");
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
	 * checks what type the value is then prints it
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextPane io )
	{
		if (isVar)
		{
			//runtime error if variable does not exist
			if(variables.get(val) == null)
            {
				logError("Nonexistant variable passed to PRINT");
                return null;
            }
			//runtime error if variable is not initialized
			if (variables.get(val).isInitialized() == false)
			{
				logError("Uninitialized variable passed to PRINT");
			}
			Interpreter.logToIo(variables.get(val).toString());
		}
		else if (isString)
		{
			Interpreter.logToIo(val + "\n");
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
	public Command execute(HashMap<String, Variable> variables, JTextPane io )
	{
		/*
		* there will be a runtime error if:
		* any of the Variables has not been created
		* or if the first two don't have values
		*/
		if (variables.get(first) == null || variables.get(second) == null || variables.get(third) == null)
		{
			logError("invalid variable passed to SUB");
			return null;
		}
		if (!variables.get(first).isInitialized() || !variables.get(second).isInitialized())
		{
			logError("invalid first or second variable passed to SUB");
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
	 * @param name the name of the new Variable
	 */
	VAR(String name)
	{
		this.name = name;
	}
	/**
	 * creates a new Variable with the given name and adds it to the HashMap
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextPane io )
	{
		//runtime error if the variable name includes '(' or ')'
		if (name.contains("(") || name.contains(")"))
		{
			logError("Variable name contains illegal characters");
			return null;
		}
		//runtime error if a variable with this name already exists
		if (variables.get(name) != null)
		{
			logError("Variable with that name already exists");
			return null;
		}
		variables.put(name, new Variable(name));
		return this.next;
	}
}


//The following commands were not in the handout
/**
 * GOTO will bring the program flow to the assigned line number
 */
class GOTO extends Command
{
	int line;
	/**
	 * @param line the line to redirect the program to
	 */
	GOTO(int line)
	{
		this.line = line;
	}
	/**
	 * GOTO brings the program to the designated line number
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextPane io) 
	{
		Command toReturn = this;
		boolean searching = true;
		while (searching)
		{
			if (toReturn == null)
			{
				searching = false;
				logError("GOTO was given an invalid line");
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

/**
 * IF takes a variable, a condition, and a value, if the variable has a value that 
 * relates to the given value using the given condition then execute will return 
 * the next Command in the list, otherwise it will return the next ENDIF statement
 * <P>
 * the valid conditions are: !, >, <, and =
 */
class IF extends Command
{
	//link to the closing ENDIF
	public Command elseIF = null;
	//contains a static list of unclosed IFs
	public static ArrayList<IF> IFs = new ArrayList<IF>();
	String var, cond;
	double val;
	/**
	 * @param variable the Variable to check against the given value
	 * @param condition the condition to use when checking the Variable and value
	 * @param value the value to check against the given Variable
	 */
	IF(String variable,String condition, double value)
	{
		var = variable;
		cond = condition;
		val = value;
		IFs.add(this);
	}
	/**
	 * returns the next Command if true, otherwise return the next ENDIF
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextPane io) 
	{
		//runtime error if the Variable does not exist
		if(variables.get(var) == null)
		{
			logError("Nonexistant variable passed to IF");
		    return null;
		}
		//runtime error if the Variable is not initialized
		if(!variables.get(var).isInitialized())
		{
			logError("Uninitialized variable passed to IF");
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

/**
 * ENDIF will close off the previous IF command
 */
class ENDIF extends Command
{
	/**
	 * takes the most recent IF if the static list of unclosed IFs and point its 
	 * else element to this ENDIF
	 */
	ENDIF()
	{
		IF.IFs.remove(IF.IFs.size()-1).elseIF = this;
	}
	/**
	 * just returns the next Command in the list
	 * {@inheritDoc}
	 */
	public Command execute(HashMap<String, Variable> variables, JTextPane io) 
	{
		return next;
	}
}
