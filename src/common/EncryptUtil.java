package common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    public static String getMd5Hash(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(string.getBytes());
            StringBuffer sb = new StringBuffer();
            for(int i = 0;i < digest.length;i++) {
                String hex = Integer.toHexString(0xFF & digest[i]);
                if(hex.length() == 1) {
                    hex = "0" + hex;
                }
                sb.append(hex);
            }
            return sb.toString().toLowerCase();
        }
        catch(NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex.getClass().getSimpleName() + ": " + ex.getMessage() +
                    " caught when building MD5 hash");
        }
    }
}
