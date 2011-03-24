package web;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import exception.DaoException;
import exception.UserNotFoundException;

public class User implements Serializable {
    public static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(User.class);

    public static class Factory {
        public static final String STMT_QUERY_USER_ID = "select user_id from user where username=?";

        public static final String STMT_QUERY_USER_DATA = "select menu.menu_id,menu_detail.name,menu_detail.create,menu_detail.search,menu_detail.update,menu_detail.delete from user,role,menu,menu_detail where role.role = user.group and menu.role = role.role and menu_detail.menu_id = menu.menu_id and user.username= ?";

        public static final String STMT_QUERY_USER_DATA_SUBMENU = "select submenu.submenu from submenu where submenu.menu_id= ?";

        public static final String STMT_QUERY_USER_QUERY = "select query.insert,query.update,query.delete,query.select from `query` where menu_id=?";

        public static final String STMT_QUERY_USER = "select username, password, full_name, nik, status, failed_logins, password_expiry,email_address,department,division,address,group from user where user_id=? ";

        public static final String STMT_QUERY_MENU = "select t4.Name from menu t1, role t2, user t3, menu_detail t4 where t1.role = t2.role and t2.role = t3.group and t3.user_id = ? and t4.menu_id = t1.menu_id";

        public static final String STMT_QUERY_USERS = "select user_id, username, full_name, nik, status, failed_logins, password_expiry,email_address,department,division,address,user.group from user";

        public static final String STMT_QUERY_USERS_ADMIN = "select u1.username,u1.user_id,u1.full_name,u1.nik,u1.status,u1.failed_logins, u1.password_expiry,u1.email_address,u1.department,u1.division,u1.address from user u1";

        public static final String STMT_QUERY_COUNT_USERS = "select count(user_id) from user";

        public static final String STMT_QUERY_USER_ROLES = "select role from user_role where role=?";

        public static final String STMT_UPDATE_USER = "update user set username=?, password=?, full_name=?, nik=?, status=?, failed_logins=?, password_expiry=?, email_address=?,department=?,division=?,address=?,user.group=? where user_id=?";

        public static final String STMT_INSERT_USER = "insert into user(username, password, full_name, nik, status, failed_logins, password_expiry,email_address,department,division,address,flag,user.group) values (?, ?, ?, ?, ?, ?, ?, ?,?,?,?,0,?)";

        public static final String STMT_DELETE_USER = "delete from user where username=?";

        public static final String STMT_DEACTIVE_USER = "update user set flag=1,status=1 where username=?";

        public static final String STMT_QUERY_INSERTED_USER_ID = "select last_insert_id()";

        public static final String STMT_QUERY_USER_ROLE = "select role from user_role where role=? and t1.role=t2.group";

        public static final String STMT_DELETE_USER_ROLE = "delete from user_role where role=?";

        public static final String STMT_INSERT_USER_ROLE = "insert into user_role(user_id, role) values (?, ?)";

        public static final String STMT_INSERT_USER_AUDIT = "insert into user_audit(user,ip_address,action) values (?,?,?)";

        public static ArrayList<String> getMenus(Connection conn, int userid)
                throws DaoException {
            try {
                PreparedStatement stmt = conn
                        .prepareStatement(STMT_QUERY_MENU);
                stmt.setInt(1, userid);
                ArrayList<String> result = new ArrayList<String>();
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    result.add(rs.getString(1));
                }
                return result;
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
        }

        public static int countNumberOfUsers(Connection conn)
                throws DaoException {
            try {
                PreparedStatement stmt = conn
                        .prepareStatement(STMT_QUERY_COUNT_USERS);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new DaoException("Error retrieving count of users");
                }
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
        }

        public static int countNumberOfUsersLike(Connection conn,
                                                 String filter, String field) throws DaoException {
            try {
                PreparedStatement stmt = conn
                        .prepareStatement(STMT_QUERY_COUNT_USERS + " where "
                                + filter + " like '%" + field + "%'");
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new DaoException("Error retrieving count of users");
                }
            } catch (SQLException ex) {
                throw new DaoException(ex);
            }
        }

        public static int countNumberOfUsersLikeAdmin(Connection conn,
                                                      String filter, String field, int userid) throws DaoException {
            try {
                PreparedStatement stmt = conn
                        .prepareStatement(STMT_QUERY_COUNT_USERS + " where u1."
                                + filter + " like '%" + field + "%' ");
                stmt.setInt(1, userid);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new DaoException("Error retrieving count of users");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DaoException(ex);

            }
        }

        public static List<User> list(Connection conn, int start, int rows)
                throws DaoException {
            List<User> users = new ArrayList<User>();

            try {
                PreparedStatement stmt;
                if ((start == -1) || (rows == -1)) {
                    stmt = conn.prepareStatement(STMT_QUERY_USERS);
                } else {
                    stmt = conn.prepareStatement(STMT_QUERY_USERS
                            + " limit ?,?");
                    stmt.setInt(1, start);
                    stmt.setInt(2, rows);
                }

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt(1));
                    user.setUsername(rs.getString(2));
                    user.setFullName(rs.getString(3));
                    user.setNik(rs.getString(4));
                    user.setStatus(rs.getInt(5));
                    user.setFailedLogins(rs.getInt(6));
                    user.setPasswordExpiry(rs.getDate(7));
                    user.setEmailAddress(rs.getString(8));
                    user.setDepartemen(rs.getString(9));
                    user.setDivision(rs.getString(10));
                    user.setAddress(rs.getString(11));
                    user.setGroup(rs.getString(12));
                    users.add(user);
                }

                return users;
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DaoException(ex);
            }
        }

        public static List<User> listlike(Connection conn, String filter,
                                          String field, int start, int rows) throws DaoException {
            List<User> users = new ArrayList<User>();

            try {
                PreparedStatement stmt, stmtrole;
                if ((start == -1) || (rows == -1)) {
                    stmt = conn.prepareStatement(STMT_QUERY_USERS);
                } else {
                    stmt = conn.prepareStatement(STMT_QUERY_USERS + " where "
                            + filter + " like '%" + field + "%' limit ?,?");
                    stmt.setInt(1, start);
                    stmt.setInt(2, rows);
                }

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt(1));
                    user.setUsername(rs.getString(2));
                    user.setFullName(rs.getString(3));
                    user.setNik(rs.getString(4));
                    user.setStatus(rs.getInt(5));
                    user.setFailedLogins(rs.getInt(6));
                    user.setPasswordExpiry(rs.getDate(7));
                    user.setEmailAddress(rs.getString(8));
                    user.setDepartemen(rs.getString(9));
                    user.setDivision(rs.getString(10));
                    user.setAddress(rs.getString(11));
                    user.setGroup(rs.getString(12));

                    stmtrole = conn.prepareStatement(STMT_QUERY_USER_ROLES);
                    stmtrole.setInt(1, Integer.parseInt(user.getGroup()));
                    ResultSet rsRole = stmtrole.executeQuery();
                    Set<Integer> setRole = new HashSet<Integer>();
                    while (rsRole.next()) {
                        setRole.add(rsRole.getInt(1));
                    }
                    user.setRoles(setRole);
                    users.add(user);

                }

                return users;
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DaoException(ex);
            }
        }

        public static List<User> listlikeadmin(Connection conn, String filter,
                                               String field, int userid, int start, int rows) throws DaoException {
            List<User> users = new ArrayList<User>();
            try {
                PreparedStatement stmt, stmtrole;
                if ((start == -1) || (rows == -1)) {
                    stmt = conn.prepareStatement(STMT_QUERY_USERS_ADMIN + " where u1.user_id=?");
                    stmt.setInt(1, userid);
                } else {
                    stmt = conn.prepareStatement(STMT_QUERY_USERS_ADMIN + "  where u1."
                            + filter + " like '%" + field + "%'  limit ?,?");
                    stmt.setInt(1, userid);
                    stmt.setInt(2, start);
                    stmt.setInt(3, rows);
                }

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString(1));
                    user.setUserId(rs.getInt(2));

                    user.setFullName(rs.getString(3));
                    user.setNik(rs.getString(4));
                    user.setStatus(rs.getInt(5));
                    user.setFailedLogins(rs.getInt(6));
                    user.setPasswordExpiry(rs.getDate(7));
                    user.setEmailAddress(rs.getString(8));
                    user.setDepartemen(rs.getString(9));
                    user.setDivision(rs.getString(10));
                    user.setAddress(rs.getString(11));

                    stmtrole = conn.prepareStatement(STMT_QUERY_USER_ROLES);
                    stmtrole.setInt(1, rs.getInt(2));
                    ResultSet rsRole = stmtrole.executeQuery();
                    Set<Integer> setRole = new HashSet<Integer>();
                    while (rsRole.next()) {
                        setRole.add(rsRole.getInt(1));
                    }
                    user.setRoles(setRole);
                    users.add(user);

                }

                return users;
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DaoException(ex);
            }
        }

        public static User loadByUsername(Connection conn, String username)
                throws UserNotFoundException, DaoException {
            PreparedStatement stmt;

            try {
                stmt = conn.prepareStatement(STMT_QUERY_USER_ID);
                stmt.setString(1, username);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return load(conn, rs.getInt(1));
                } else {
                    throw new UserNotFoundException(
                            "No user was found for username '" + username + "'");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DaoException(ex);
            }
        }        

        public static User load(Connection conn, int userId)
                throws UserNotFoundException, DaoException {
            try {
                // get the user            	
                PreparedStatement stmt = conn.prepareStatement(STMT_QUERY_USER);
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(userId);
                    user.setUsername(rs.getString(1));
                    user.setPassword(rs.getString(2));
                    user.setFullName(rs.getString(3));
                    user.setNik(rs.getString(4));
                    user.setStatus(rs.getInt(5));
                    user.setFailedLogins(rs.getInt(6));
                    user.setPasswordExpiry(rs.getDate(7));
                    user.setEmailAddress(rs.getString(8));
                    user.setDepartemen(rs.getString(9));
                    user.setDivision(rs.getString(10));
                    user.setAddress(rs.getString(11));
                    user.setGroup(rs.getString(12));                        
                    // load the roles
                    PreparedStatement stmt2 = conn
                            .prepareStatement(STMT_QUERY_USER_ROLES);
                    stmt2.setInt(1, userId);
                    ResultSet rs2 = stmt2.executeQuery();
                    int role;
                    Set<Integer> setRole = new HashSet<Integer>();
                    while (rs2.next()) {
                        // user.getRoles().add(rs2.getInt(1));
                        role = rs2.getInt(1);
                        // setRole=new HashSet();
                        setRole.add(role);

                    }
                    user.setRoles(setRole);                    
                    // load the menus
                    user.setMenus(getMenus(conn, userId));
                    return user;
                } else {
                    throw new UserNotFoundException(
                            "No user found with user_id " + userId);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DaoException(ex);
            }
        }        

        public static void newsave(Connection conn, User user) throws DaoException {
            PreparedStatement stmt;
            try {
                if (user.getUserId() == -1) {
                    // create new user
                    stmt = conn.prepareStatement(STMT_INSERT_USER);
                    stmt.setString(1, user.getUsername());
                    stmt.setString(2, user.getPassword());
                    stmt.setString(3, user.getFullName());
                    stmt.setString(4, user.getNik());
                    stmt.setInt(5, user.getStatus());
                    stmt.setInt(6, user.getFailedLogins());
                    stmt.setDate(7, user.getPasswordExpiry());
                    stmt.setString(8, user.getEmailAddress());
                    stmt.setString(9, user.getDepartemen());
                    stmt.setString(10, user.getDivision());
                    stmt.setString(11, user.getAddress());
                    stmt.setString(12, user.getGroup());
                    stmt.executeUpdate();

                    stmt = conn.prepareStatement(STMT_QUERY_INSERTED_USER_ID);

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    } else {
                        throw new DaoException(
                                "Unable to retrieve last insert id while inserting new user");
                    }
                } else {
                    // update existing user
                    stmt = conn.prepareStatement(STMT_UPDATE_USER);

                    stmt.setString(1, user.getUsername());
                    stmt.setString(2, user.getPassword());
                    stmt.setString(3, user.getFullName());
                    stmt.setString(4, user.getNik());
                    stmt.setInt(5, user.getStatus());
                    stmt.setInt(6, user.getFailedLogins());
                    stmt.setDate(7, user.getPasswordExpiry());
                    stmt.setString(8, user.getEmailAddress());
                    stmt.setString(9, user.getDepartemen());
                    stmt.setString(10, user.getDivision());
                    stmt.setString(11, user.getAddress());
                    stmt.setString(12, user.getGroup());
                    stmt.setInt(13, user.getUserId());
                    stmt.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DaoException(ex);
            }
        }

        public static void delete(Connection conn, String username)
                throws UserNotFoundException, DaoException {
            try {
                // delete the user
                PreparedStatement stmt = conn
                        .prepareStatement(STMT_DELETE_USER);
                stmt.setString(1, username);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DaoException(ex);
            }
        }

        public static void deactive(Connection conn, String username)
                throws UserNotFoundException, DaoException {
            try {
                // delete the user
                PreparedStatement stmt = conn
                        .prepareStatement(STMT_DEACTIVE_USER);
                stmt.setString(1, username);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DaoException(ex);
            }
        }
    }

    public static final int STATUS_ACTIVE = 0;

    public static final int STATUS_DEACTIVE = 1;

    public static final int STATUS_LOCKED = 2;

    public static final Integer ROLE_ADMINISTRATOR = 0;

    public static final Integer ROLE_STUDENT = 1;

    public static final Integer ROLE_LECTURE = 2;

    public static final Integer ROLE_SUPERUSER = 3;

    public static final Integer[] ROLES = new Integer[]{ROLE_ADMINISTRATOR, ROLE_STUDENT, ROLE_LECTURE, ROLE_SUPERUSER};

    int userId;

    String username;

    String password;

    int status;

    int failedLogins;

    Date passwordExpiry;

    String fullName;

    String nik;

    String emailAddress;

    String departemen;

    String division;

    String address;

    String group;

    ArrayList<String> menus;

    Set<Integer> roles = new HashSet<Integer>();

    public User() {
        userId = -1;
    }

    public boolean hasRole(Integer role) {
        if (roles.contains(role))
            return true;
        else
            return false;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public Set<Integer> getRoles() {
        return roles;
    }

    public void setRoles(Set<Integer> roles) {
        this.roles = roles;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFailedLogins() {
        return failedLogins;
    }

    public void setFailedLogins(int failedLogins) {
        this.failedLogins = failedLogins;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPasswordExpiry() {
        return passwordExpiry;
    }

    public void setPasswordExpiry(Date passwordExpiry) {
        this.passwordExpiry = passwordExpiry;
    }

    public String getDepartemen() {
        return departemen;
    }

    public void setDepartemen(String departemen) {
        this.departemen = departemen;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ArrayList<String> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<String> menus) {
        this.menus = menus;
    }
}
