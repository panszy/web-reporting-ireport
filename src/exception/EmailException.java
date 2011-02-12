package exception;

public class EmailException extends Exception{
    
    public EmailException()
    {
        super();
    }
    public EmailException(String message)
    {
        super(message);
    }
    public EmailException(String message,Exception ex)
    {
        super(message,ex);
    }
    public EmailException(Exception ex)
    {
        super(ex);
    }

}
