package business.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class LibraryMemberException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = -518543749022324138L;
    public LibraryMemberException(String msg) {
        super(msg);
    }
}
