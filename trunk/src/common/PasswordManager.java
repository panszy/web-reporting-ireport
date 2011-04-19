package common;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import web.Configuration;
import exception.DaoException;
import exception.InvalidPasswordException;
import web.User;

public class PasswordManager {
    public static final String STMT_QUERY_PASSWORD_HISTORY_COUNT_PASSWORD_MATCH = "select count(user_password_history_id) from user_password_history where user_id=? and password=?";

    public static final String STMT_INSERT_PASSWORD_HISTORY = "insert into user_password_history(user_id, password) values (?, ?)";

    public static final String STMT_QUERY_OLDEST_PASSWORD_HISTORY = "select * from (select user_password_history_id, ROW_NUMBER() OVER(ORDER BY user_password_history_id DESC) AS RN from user_password_history where user_id=? order by user_password_history_id desc) where RN between ? and ?";

    public static final String STMT_DELETE_PASSWORD_HISTORY = "delete from user_password_history where user_password_history_id = ?";

    public static void changePassword(Connection conn, User user,
            String oldPassword, String newPassword, String confirmedNewPassword)
            throws InvalidPasswordException, DaoException {
        if (user.getPassword().compareTo(EncryptUtil.getMd5Hash(oldPassword)) != 0) {
            throw new InvalidPasswordException(
                    "The current password you entered is invalid");
        }
        if (newPassword.length() < 8)
            throw new InvalidPasswordException(
                    "Password length must be at least 8 characters");
        Pattern passPattern = Pattern
                .compile("([0-9]+[a-z]+[A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]*)|"+
                        "([0-9]+[a-z]+[A-Z]*[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+)|"+
                        "([0-9]+[a-z]*[A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+)|"+
                        "([0-9]*[a-z]+[A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+)|"+

                        "([a-z]+[0-9]+[A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]*)|"+
                        "([a-z]+[0-9]+[A-Z]*[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+)|"+
                        "([a-z]*[0-9]+[A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+)|"+
                        "([a-z]+[0-9]*[A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+)|"+

                        "([a-z]+[A-Z]+[0-9]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]*)|"+
                        "([a-z]+[A-Z]*[0-9]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+)|"+
                        "([a-z]*[A-Z]+[0-9]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+)|"+
                        "([a-z]+[A-Z]+[0-9]*[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+)|"+

                        "([a-z]+[A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]*[0-9]+)|"+
                        "([a-z]+[A-Z]*[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[0-9]+)|"+
                        "([a-z]*[A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[0-9]+)|"+
                        "([a-z]+[A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[0-9]*)|"+

                        "([A-Z]+[a-z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]*[0-9]+)|"+
                        "([A-Z]*[a-z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[0-9]+)|"+
                        "([A-Z]+[a-z]*[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[0-9]+)|"+
                        "([A-Z]+[a-z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[0-9]*)|"+

                        "([A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]*[0-9]+[a-z]+)|"+
                        "([A-Z]*[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[0-9]+[a-z]+)|"+
                        "([A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[0-9]+[a-z]*)|"+
                        "([A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[0-9]*[a-z]+)|"+

                        "([A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]*[a-z]+[0-9]+)|"+
                        "([A-Z]*[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[a-z]+[0-9]+)|"+
                        "([A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[a-z]*[0-9]+)|"+
                        "([A-Z]+[\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[a-z]+[0-9]*)|"+
                        
                        "([\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]*[A-Z]+[a-z]+[0-9]+)|"+
                        "([\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[A-Z]*[a-z]+[0-9]+)|"+
                        "([\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[A-Z]+[a-z]*[0-9]+)|"+
                        "([\\\\|\\(\\)\\[\\]\\{\\}\\^\\+\\?\\.\\<\\>\\*\\!\\@\\#\\%\\=\\-]+[A-Z]+[a-z]+[0-9]*)"


);
        Matcher passmatcher = passPattern.matcher(newPassword);
        boolean passmatch = passmatcher.matches();
        if (!passmatch) {
            throw new InvalidPasswordException(
                    "Password must be combinations 3 from 4 type (lowercase, uppercase, digit, alphanumeric)");
        }

        if (newPassword.compareTo(confirmedNewPassword) != 0) {
            throw new InvalidPasswordException(
                    "New password and confirmed new password does not match");
        }

        validatePassword(conn, user, newPassword);

        checkPasswordHistory(conn, user, newPassword);

        // all passed, update the password
        user.setPassword(EncryptUtil.getMd5Hash(newPassword));

        // add the password expiration date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, Configuration.PASSWORD_VALIDITY_YEAR);
        cal.add(Calendar.MONTH, Configuration.PASSWORD_VALIDITY_MONTH);
        cal.add(Calendar.DATE, Configuration.PASSWORD_VALIDITY_DAY);
        cal.add(Calendar.HOUR, Configuration.PASSWORD_VALIDITY_HOUR);
        cal.add(Calendar.MINUTE, Configuration.PASSWORD_VALIDITY_MINUTE);
        user.setPasswordExpiry(new Date(cal.getTimeInMillis()));

        User.Factory.newsave(conn, user);

        // save the new password to the history
        addPasswordHistory(conn, user);
    }

    public static void resetPassword(Connection conn, User user)
            throws InvalidPasswordException, DaoException {

        // all passed, update the password
        user.setPassword(EncryptUtil.getMd5Hash("telkomsel"+user.getNik()));

        // add the password expiration date
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, Configuration.PASSWORD_VALIDITY_YEAR);
        cal.add(Calendar.MONTH, Configuration.PASSWORD_VALIDITY_MONTH);
        cal.add(Calendar.DATE, Configuration.PASSWORD_VALIDITY_DAY);
        cal.add(Calendar.HOUR, Configuration.PASSWORD_VALIDITY_HOUR);
        cal.add(Calendar.MINUTE, Configuration.PASSWORD_VALIDITY_MINUTE);
        user.setPasswordExpiry(new Date(cal.getTimeInMillis()));

        User.Factory.newsave(conn, user);

        // save the new password to the history
        addPasswordHistory(conn, user);
    }

    public static void validatePassword(Connection conn, User user,
            String password) throws InvalidPasswordException {
        // check the password length and alphanumeric contents
        if (password.length() < 8)
            throw new InvalidPasswordException(
                    "Password length must be at least 8 characters");
    }

    public static void checkPasswordHistory(Connection conn, User user,
            String password) throws InvalidPasswordException, DaoException {
        // check whether the user already uses this password within history
        PreparedStatement stmt=null;
        ResultSet rs=null;
        try {
            stmt = conn
                    .prepareStatement(STMT_QUERY_PASSWORD_HISTORY_COUNT_PASSWORD_MATCH);
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, EncryptUtil.getMd5Hash(password));

            rs = stmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    throw new InvalidPasswordException(
                            "Password already used within history, please choose another one");
                }
            } else {
                throw new DaoException();
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        finally{
            try{
            	if(rs!=null)
            rs.close();
            	if(stmt!=null)
            stmt.close();
            }catch(Throwable t)
            {
                t.printStackTrace();
            }
        }
    }

    public static void addPasswordHistory(Connection conn, User user)
            throws DaoException {
        PreparedStatement stmt =null;
        ResultSet rs=null;
        try {

            stmt= conn
                    .prepareStatement(STMT_INSERT_PASSWORD_HISTORY);
            stmt.setInt(1, user.getUserId());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();

            // remove the oldest history
            stmt = conn.prepareStatement(STMT_QUERY_OLDEST_PASSWORD_HISTORY);
            stmt.setInt(1, user.getUserId());
            stmt.setInt(2, Configuration.PASSWORD_HISTORY_COUNT);
            stmt.setInt(3, Configuration.PASSWORD_HISTORY_COUNT + 1);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int row = rs.getInt(1);

                stmt = conn.prepareStatement(STMT_DELETE_PASSWORD_HISTORY);
                stmt.setInt(1, row);
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
        finally{
            try{
            rs.close();
            stmt.close();
            }catch(Throwable t)
            {
                t.printStackTrace();
            }
        }
    }
}
