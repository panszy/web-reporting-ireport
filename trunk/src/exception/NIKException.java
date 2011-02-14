package exception;

public class NIKException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NIKException()
    {
        super();
    }
    
    public NIKException(String message)
    {
        super(message);
    }
    
    public NIKException(String message,Exception e)
    {
        super(message,e);
    }
    
    public NIKException(Exception e)
    {
        super(e);
    }

}
