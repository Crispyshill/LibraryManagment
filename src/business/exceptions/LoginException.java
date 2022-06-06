package business.exceptions;
/*
 * Excpetion class to handle login Exceptions
 */
import java.io.Serializable;

public class LoginException extends Exception implements Serializable {
	public LoginException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 8978723266036027364L;
}
