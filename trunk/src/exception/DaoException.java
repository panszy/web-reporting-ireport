package exception;

public class DaoException extends Exception {
    public static final long serialVersionUID = 1L;
    
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }
    
    public DaoException(String message, Exception ex) {
        super(message, ex);
    }
    
    public DaoException(Exception ex) {
        super(ex);
    }
}
