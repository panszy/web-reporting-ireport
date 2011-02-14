package exception;

public class AppsIDException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AppsIDException()
    {
        super();
    }
    
    public AppsIDException(String message)
    {
        super(message);
    }
    public AppsIDException(String message,Exception e)
    {
        super(message,e);
    }
    public AppsIDException(Exception e)
    {
        super(e);
    }

}
