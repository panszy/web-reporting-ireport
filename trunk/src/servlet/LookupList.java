package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import web.User;
import web.UserSession;
import database.Connector;
import exception.DaoException;
import exception.UserNotFoundException;

/**
 * Servlet implementation class LookupList
 */
public class LookupList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(LookupList.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LookupList() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String page = (String) request.getParameter("page");
		String KindOfsearch = (String) request.getParameter("KindOfsearch");
		String WordOfsearch = (String) request.getParameter("WordOfsearch");
		if (page != null) {
			UserSession userSession1 = UserSession.Factory
					.getUserSession(request);

			try {
				int total_pages = User.Factory.countNumberOfUsersLikeAdmin(
						Connector.getInstance().getConnection(), KindOfsearch,
						WordOfsearch, userSession1.getUser().getUserId());
				try {
					List<User> users = User.Factory
							.listlike(Connector.getInstance().getConnection(),
									KindOfsearch, WordOfsearch,
									10 * (Integer.parseInt(page) - 1), 10);
					request.setAttribute("listOfUser", users);
				} catch (DaoException ex) {
					ex.printStackTrace();
					logger.error(ex);
				} catch (NamingException ex) {
					ex.printStackTrace();
					logger.error(ex);
				} catch (SQLException ex) {
					logger.error(ex);
					ex.printStackTrace();
				}
				request.setAttribute(
						"total_pages",
						Integer.toString((total_pages / 10)
								+ ((total_pages % 10) > 0 ? 1 : 0)));
				request.setAttribute("pages", page);
			} catch (Throwable t) {
				// logger.warn(t);
			}
		}

		request.getRequestDispatcher("/pages/list.jsp").forward(request,
				response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {		

		if (request.getParameter("Action") != null) {

			String action = request.getParameter("Action");
			if (action.equalsIgnoreCase("Search")) {
				String KindOfsearch = (String) request.getParameter("field");
				String WordOfsearch = (String) request.getParameter("User");
				try {
					List<User> users = User.Factory.listlike(Connector
							.getInstance().getConnection(), KindOfsearch,
							WordOfsearch, 0, 10);
					int total_pages = User.Factory.countNumberOfUsersLike(
							Connector.getInstance().getConnection(),
							KindOfsearch, WordOfsearch);
					request.setAttribute("listOfUser", users);
					request.setAttribute(
							"total_pages",
							Integer.toString((total_pages / 10)
									+ ((total_pages % 10) > 0 ? 1 : 0)));
				} catch (DaoException ex) {
					ex.printStackTrace();
					// logger.error(ex);
				} catch (NamingException ex) {
					ex.printStackTrace();
					// logger.error(ex);
				} catch (SQLException ex) {
					ex.printStackTrace();
					// logger.error(ex);
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.getRequestDispatcher(
						"/pages/list.jsp?KindOfsearch=" + KindOfsearch
								+ "&WordOfsearch=" + WordOfsearch).forward(
						request, response);
			}
		}
	}

}
