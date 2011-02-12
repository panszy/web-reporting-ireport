package exception;

public class RoleException extends Exception {
    
    public RoleException()
    {
        super();
    }
    
    public RoleException(String message)
    {
        super(message);
    }
    
    public RoleException(String message,Exception ex)
    {
        super(message,ex);
    }
    public RoleException(Exception ex)
    {
        super(ex);
    }

}
