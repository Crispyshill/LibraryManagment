package business.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class BookCopyException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = -2113375063795765604L;

    public BookCopyException() {
        super();
    }
    public BookCopyException(String msg) {
        super(msg);
    }
    public BookCopyException(Throwable t) {
        super(t);
    }
}
