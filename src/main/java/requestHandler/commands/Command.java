package requestHandler.commands;

/**
 * All commands-classes which process a certain request should inherit from this class.
 */
public abstract class Command {
	
	public static final String SUCCES_VAR = "successful";
	public static final String ERROR_VAR = "error";
	public static final String INT_ERROR = "Internal error";
	public static final String ADMIN_ERROR ="You are not an admin";
	
	/**
	 * Should be overwritten by every class which inherits from this class. 
	 * @return Returns the result of the processing of the request.
	 */
	public String process(){
		return null;
	}

}
