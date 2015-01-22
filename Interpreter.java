import java.util.HashMap;
import javax.swing.JEditorPane;

public class Interpreter 
{
	protected class Variable
	{
		protected String name;
		protected double value;
		protected boolean initialized = false;
		Variable(String name)
		{
			this.name = name;
		}
		public String getName()
		{
			return name;
		}
		public double getValue()
		{
			return value;
		}
		public void setValue(double value)
		{
			this.value = value;
			if (!initialized)
			{
				initialized = true;
			}
		}
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
	}
	protected interface Command
	{
		public boolean execute(HashMap<String, Variable> variables, JEditorPane pane );
	}
	protected class ADD implements Command
	{
		protected String first, second, third;
		ADD(String first, String second, String third)
		{
			this.first = first;
			this.second = second;
			this.third = third;
		}
		public boolean execute(HashMap<String, Variable> variables, JEditorPane pane )
		{
			double f = variables.get(first).getValue();
			double s = variables.get(second).getValue();
			double t = f + s;
			variables.get(third).setValue(t);
			return true;
		}
	}
	protected class ASSIGN implements Command
	{
		protected String var;
		protected double val;
		ASSIGN(String var, double val)
		{
			this.var = var;
			this.val = val;
		}
		public boolean execute(HashMap<String, Variable> variables, JEditorPane pane )
		{
			variables.get(var).setValue(val);
			return true;
		}
	}
	protected class DIV implements Command
	{
		protected String first, second, third;
		DIV(String first, String second, String third)
		{
			this.first = first;
			this.second = second;
			this.third = third;
		}
		public boolean execute(HashMap<String, Variable> variables, JEditorPane pane )
		{
			double f = variables.get(first).getValue();
			double s = variables.get(second).getValue();
			double t = f / s;
			variables.get(third).setValue(t);
			return true;
		}
	}
	protected class ENTER implements Command
	{
		protected String var;
		ENTER(String var)
		{
			this.var = var;
		}
		public boolean execute(HashMap<String, Variable> variables, JEditorPane pane )
		{
			//variables.get(var).setValue(something);
			return true;
		}
	}
	protected class INC implements Command
	{
		protected String var;
		INC(String var)
		{
			this.var = var;
		}
		public boolean execute(HashMap<String, Variable> variables, JEditorPane pane )
		{
			variables.get(var).setValue((variables.get(var).getValue() + 1));
			return true;
		}
	}
}
