package RequestHandler.Commands;

public abstract class Command {
	
	protected final String SUCCES_VAR = "successful";
	protected final String ERROR_VAR = "error";
	protected final String INT_ERROR = "Internal error";
	protected final String ADMIN_ERROR ="You are not an admin";
	
	public String process(){
		return null;
	}

}
