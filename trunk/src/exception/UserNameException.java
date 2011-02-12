package exception;

public class UserNameException extends Exception {
 public static final long serialVersionUID = 1L;
    
    public UserNameException() {
        super();
    }
    
    public UserNameException(String message) {
        super(message);
    }
    
    public UserNameException(String message, Exception ex) {
        super(message, ex);
    }
    
    public UserNameException(Exception ex) {
        super(ex);
    }
}
