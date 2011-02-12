package exception;

public class InvalidPasswordException extends Exception {
    public static final long serialVersionUID = 1L;
    
    public InvalidPasswordException() {
        super();
    }
    
    public InvalidPasswordException(String message) {
        super(message);
    }
    
    public InvalidPasswordException(String message, Exception ex) {
        super(message, ex);
    }
    
    public InvalidPasswordException(Exception ex) {
        super(ex);
    }
}
