package exception;

public class MandatoryFieldIsEmptyException extends Exception {
    private String fieldParam = null;
    /**
     * 
     */
    private static final long serialVersionUID = -3924431125377065821L;
    
    public MandatoryFieldIsEmptyException(String fieldParam) {
        super("Mandatory field is empty:"+ fieldParam);
        this.fieldParam = fieldParam;
    }
    
    public String getFieldParam() {
        return fieldParam;
    }
}
