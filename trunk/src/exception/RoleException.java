package exception;

public class RoleException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
