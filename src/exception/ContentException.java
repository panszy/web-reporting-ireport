package exception;

public class ContentException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContentException()
    {
        super();
    }
    public ContentException(String message)
    {
        super(message);
    }
    public ContentException(String message,Exception e)
    {
        super(message,e);
    }
    
    public ContentException(Exception e)
    {
        super(e);
    }
}
