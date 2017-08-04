package RequestHandler;

/**
 * @author jocox
 * 
 * All Request Handler are subclasses of this class. If the request was done successfully
 * the Handler classes return the desired data, other wise they return null.
 */
public abstract class Command {
	
	public String process(){
		return null;
	}

}
