package business.exceptions;
/*
 * Exception to define LibrarySystemExceptions
 */
import java.io.Serial;
import java.io.Serializable;

public class LibrarySystemException extends Exception implements Serializable {
	@Serial
	private static final long serialVersionUID = 3326915348398932420L;
	public LibrarySystemException(String msg) {
		super(msg);
	}

}
