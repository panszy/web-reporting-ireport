package common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    static SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
    
    public static String getDisplayDate(Date date) {
        return sdf.format(date);
    }
}
