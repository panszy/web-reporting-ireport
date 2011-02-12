package exception;

public class AccountLockedException extends LoginException {
    public static final long serialVersionUID = 1L;
    
    public AccountLockedException() {
        super();
    }
    
    public AccountLockedException(String message) {
        super(message);
    }
    
    public AccountLockedException(String message, Exception ex) {
        super(message, ex);
    }
    
    public AccountLockedException(Exception ex) {
        super(ex);
    }
}
