package exception;

public class LoginException extends Exception {
    public static final long serialVersionUID = 1L;
    
    public LoginException() {
        super();
    }
    
    public LoginException(String message) {
        super(message);
    }
    
    public LoginException(String message, Exception ex) {
        super(message, ex);
    }
    
    public LoginException(Exception ex) {
        super(ex);
    }
}
