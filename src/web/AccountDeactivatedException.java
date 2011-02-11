package web;

public class AccountDeactivatedException extends LoginException {
    public static final long serialVersionUID = 1L;
    
    public AccountDeactivatedException() {
        super();
    }
    
    public AccountDeactivatedException(String message) {
        super(message);
    }
    
    public AccountDeactivatedException(String message, Exception ex) {
        super(message, ex);
    }
    
    public AccountDeactivatedException(Exception ex) {
        super(ex);
    }
}
