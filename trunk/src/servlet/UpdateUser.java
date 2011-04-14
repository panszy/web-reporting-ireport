package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Set;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import exception.DaoException;
import exception.EmailException;
import exception.InvalidPasswordException;
import exception.NIKException;
import common.PasswordManager;
import database.Connector;
import web.HttpConstants;
import web.User;
import exception.UserNotFoundException;
import web.UserSession;

public class UpdateUser extends HttpServlet {
	public static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(SearchUser.class);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String strUsername = (String) request.getParameter("user");
		Map<String, String> groupMap = new HashMap<String, String>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		if (strUsername != null) {
			try {
				conn = Connector.getInstance().getConnection();
				pstmt = conn.prepareStatement("select role,name from role");
				rs = pstmt.executeQuery();
				while (rs.next()) {
					groupMap.put(rs.getString(1), rs.getString(2));
				}

				User user = User.Factory.loadByUsername(Connector.getInstance()
						.getConnection(), strUsername);
				request.setAttribute(HttpConstants.ATTR_NAME_USER, user);
				request.setAttribute("groupMap", groupMap);

				request.getRequestDispatcher("/pages/admin/user-update.jsp")
						.forward(request, response);

			} catch (Throwable t) {
				logger.warn("Unable to load user with user_id '" + strUsername
						+ "'");
				request.getRequestDispatcher("/pages/admin/user-search.jsp")
						.forward(request, response);

			}

		}		
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// String username = (String)request.getParameter("username");
		@SuppressWarnings("unused")
		String fullname1, nik1, email1, department1, division1, address1;
		int status1;
		@SuppressWarnings("unused")
		Set<Integer> roleSet1;
		StringBuffer buff = new StringBuffer();
		Map<String, Integer> statusMap = new HashMap<String, Integer>();
		statusMap.put("active", 0);
		statusMap.put("deactive", 1);
		statusMap.put("locked", 2);
		if (request.getParameter("origin_username") == null)
			doGet(request, response);
		else {
			String originusername = (String) request
					.getParameter("origin_username");
			String fullname = (String) request
					.getParameter(HttpConstants.HTTP_VAR_FULLNAME);

			String nik = (String) request
					.getParameter(HttpConstants.HTTP_VAR_NIK);
			String emailaddress = (String) request
					.getParameter(HttpConstants.HTTP_VAR_EMAIL_ADDRESS);
			String department = (String) request
					.getParameter(HttpConstants.HTTP_VAR_DEPARTEMEN);
			String division = (String) request
					.getParameter(HttpConstants.HTTP_VAR_DIVISION);
			String address = (String) request
					.getParameter(HttpConstants.HTTP_VAR_ADDRESS);
			String Action = (String) request.getParameter("Action");
			int status = (Integer) statusMap
					.get(request.getParameter("status"));
			String group = request.getParameter("group");
			if (Action.equalsIgnoreCase("Update")) {
				User user = null;
				try {
					Connection conn = Connector.getInstance().getConnection();
					user = User.Factory.loadByUsername(conn, originusername);

					fullname1 = user.getFullName();
					nik1 = user.getNik();
					email1 = user.getEmailAddress();
					department1 = user.getDepartemen();
					division1 = user.getDivision();
					address1 = user.getAddress();
					status1 = user.getStatus();
					roleSet1 = user.getRoles();
					user.setFullName(fullname);
					user.setEmailAddress(emailaddress);
					user.setUsername(originusername);
					user.setNik(nik);
					user.setStatus(status);
					user.setDepartemen(department);
					user.setDivision(division);
					user.setAddress(address);
					user.setGroup(group);
					user.setFailedLogins(0);
					Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
					Matcher matcher = pattern
							.matcher(request
									.getParameter(HttpConstants.HTTP_VAR_EMAIL_ADDRESS));
					boolean match = matcher.matches();
					if (!match) {
						throw new EmailException("Invalid Email Format");
					}
					if (request.getParameter(HttpConstants.HTTP_VAR_NIK) == "") {
						throw new NIKException("You must Fill NIK column");
					}
					User.Factory.newsave(Connector.getInstance()
							.getConnection(), user);
					if (fullname1.compareTo(fullname) != 0)
						buff.append("old FullName=" + fullname1
								+ " new Fullname=" + fullname);
					if (nik1.compareTo(nik) != 0)
						buff.append(",old NIK=" + nik1 + " new NIK=" + nik);
					if (email1.compareTo(emailaddress) != 0)
						buff.append(",old Email=" + email1 + " new Email="
								+ emailaddress);
					if (department1.compareTo(department) != 0)
						buff.append(",old department=" + department1
								+ " new Department=" + department);
					if (division1.compareTo(division) != 0)
						buff.append(",old division=" + division1
								+ " new Division=" + division);
					if (status1 != status)
						buff.append("old status=" + status1 + " new Status="
								+ status);
					request.setAttribute(HttpConstants.ATTR_NAME_USER, user);
					request.getRequestDispatcher(
							"/pages/admin/user-update-success.jsp").forward(
							request, response);

				} catch (UserNotFoundException e) {
					logger.error(e);
				} catch (DaoException e) {
					logger.error(e);
				} catch (NamingException e) {
					logger.error(e);
				} catch (SQLException e) {
					logger.error(e);
				} catch (EmailException e) {
					e.printStackTrace();
					request.setAttribute(HttpConstants.ATTR_NAME_USER, user);
					request.setAttribute("error", e.getMessage());
					request.getRequestDispatcher("/pages/admin/user-update.jsp")
							.forward(request, response);

				} catch (NIKException e) {
					e.printStackTrace();
					request.setAttribute(HttpConstants.ATTR_NAME_USER, user);
					request.setAttribute("error", e.getMessage());
					request.getRequestDispatcher("/pages/admin/user-update.jsp")
							.forward(request, response);

				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Connection conn = Connector.getInstance().getConnection();
					PreparedStatement pstmt = conn
							.prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
					UserSession userSession = UserSession.Factory
							.getUserSession(request);

					pstmt.setString(1, userSession.getUser().getUsername());
					pstmt.setString(2, request.getRemoteAddr());
					pstmt.setString(3, "Update " + user.getUsername() + " by "
							+ userSession.getUser().getUsername()
							+ " Changed was done :" + buff.toString());
					pstmt.executeUpdate();

				} catch (NamingException ex) {
					logger.error(ex);
				} catch (SQLException ex) {
					logger.error(ex);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (Action.equalsIgnoreCase("Reset Password")) {
				User user = null;
				try {
					user = User.Factory.loadByUsername(Connector.getInstance()
							.getConnection(), originusername);
				} catch (UserNotFoundException e) {
					logger.error(e);
				} catch (DaoException e) {
					logger.error(e);
				} catch (NamingException e) {
					logger.error(e);
				} catch (SQLException e) {
					logger.error(e);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					PasswordManager.resetPassword(Connector.getInstance()
							.getConnection(), user);
				} catch (DaoException ex) {
					logger.error(ex);
				} catch (NamingException ex) {
					logger.error(ex);
				} catch (SQLException ex) {
					logger.error(ex);
				} catch (InvalidPasswordException ex) {
					logger.error(ex);
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.getRequestDispatcher(
						"/pages/admin/user-delete-success.jsp?message=reset&nik="
								+ user.getNik()).forward(request, response);
			}
		}
	}
}
