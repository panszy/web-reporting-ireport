package web;

public class UserNotFoundException extends Exception {
    public static final long serialVersionUID = 1L;
    
    public UserNotFoundException() {
        super();
    }
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Exception ex) {
        super(message, ex);
    }
    
    public UserNotFoundException(Exception ex) {
        super(ex);
    }
}
