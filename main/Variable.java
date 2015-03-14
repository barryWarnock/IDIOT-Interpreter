package main;

/**
 * @author Barry Warnock
 * Variable is the class that will represent our IDIOT variables, each Variable tracks
 * its name, its value, and whether or not it has been initialized yet
 */
class Variable
{
	protected String name;
	protected double value;
	protected boolean initialized = false;
	/**
	 * the Variable constructor sets the Variables name
	 * @param name the name of the new interpreter
	 */
	Variable(String name)
	{
		this.name = name;
	}
	/**
	 * @return the current value of the Variable
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
	 * @return true if the variable has been initialized or false if it has not
	 */
	public boolean isInitialized()
	{
		return initialized;
	}
	/**
	 * @return the result of calling hashCode() on the Variables name
	 */
	public int hashCode()
	{
		return name.hashCode();
	}
	/**
	 * @param other the Variable to compare for equality
	 * @return true if the two Variables share the same value, otherwise false
	 */
	public boolean equals(Variable other)
	{
		if(this.value == other.value)
		{
			return true;
		}
		return false;
	}
	/**
	 * @return a String consisting of the value of the Variable followed by a newline, or 
	 * if it has not yet been initialized then a message is returned saying that it has not been 
	 * initialized
	 */
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