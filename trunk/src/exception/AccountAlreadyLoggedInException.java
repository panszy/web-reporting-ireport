package exception;

public class AccountAlreadyLoggedInException extends LoginException {
    public static final long serialVersionUID = 1L;
    
    public AccountAlreadyLoggedInException() {
        super();
    }
    
    public AccountAlreadyLoggedInException(String message) {
        super(message);
    }
    
    public AccountAlreadyLoggedInException(Exception ex) {
        super(ex);
    }
    
    public AccountAlreadyLoggedInException(String message, Exception ex) {
        super(message, ex);
    }
}
