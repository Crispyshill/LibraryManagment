package business.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class CheckOutException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = -2123503267327159288L;

    public CheckOutException(String msg) {
        super(msg);
    }
}
