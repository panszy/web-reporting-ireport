package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.omg.CORBA.Request;
import exception.DaoException;
import exception.InvalidPasswordException;
import common.PasswordManager;
import database.Connector;
import web.HttpConstants;
import web.User;
import exception.UserNotFoundException;
import web.UserSession;

public class SearchUser extends HttpServlet {
    public static final long serialVersionUID = 1L;

    static Logger logger = Logger.getLogger(SearchUser.class);

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
                        WordOfsearch,userSession1.getUser().getUserId());
                try {
                    List<User> users = User.Factory
                            .listlike(Connector.getInstance().getConnection(),
                                    KindOfsearch, WordOfsearch,10 * (Integer
                                            .parseInt(page) - 1), 10);
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
                request.setAttribute("total_pages", Integer
                        .toString((total_pages / 10)
                                + ((total_pages % 10) > 0 ? 1 : 0)));
                request.setAttribute("pages", page);
            } catch (Throwable t) {
                //logger.warn(t);
            }
        }

        request.getRequestDispatcher("/pages/admin/user-search.jsp").forward(
                request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        UserSession userSession1 = UserSession.Factory
        .getUserSession(request);

        if (request.getParameter("Action") != null) {
          
            String action = request.getParameter("Action");
            if (action.equalsIgnoreCase("Search")) {
                String KindOfsearch = (String) request.getParameter("field");
                String WordOfsearch = (String) request.getParameter("User");
                try {
                    List<User> users = User.Factory.listlike(Connector.getInstance().getConnection(), KindOfsearch,
                            WordOfsearch, 0, 10);
                    int total_pages = User.Factory.countNumberOfUsersLike(
                    		Connector.getInstance().getConnection(),
                            KindOfsearch, WordOfsearch);
                    request.setAttribute("listOfUser", users);
                    request.setAttribute("total_pages", Integer
                            .toString((total_pages / 10)
                                    + ((total_pages % 10) > 0 ? 1 : 0)));
                } catch (DaoException ex) {
                    ex.printStackTrace();
                   // logger.error(ex);
                } catch (NamingException ex) {
                    ex.printStackTrace();
                   // logger.error(ex);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                  //  logger.error(ex);
                } catch (Exception e) {
					e.printStackTrace();
				}
                request.getRequestDispatcher(
                        "/pages/admin/user-search.jsp?KindOfsearch="
                                + KindOfsearch + "&WordOfsearch="
                                + WordOfsearch).forward(request, response);
            } else if (action.equalsIgnoreCase("Delete")) {
                if (request.getParameterValues("deleted") != null) {
                    String[] delete = (String[]) request
                            .getParameterValues("deleted");
                    for (String str : delete)

                        try {

                            Connection conn2 = Connector.getInstance().getConnection();
                            PreparedStatement pstmt = conn2
                                    .prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
                            UserSession userSession = UserSession.Factory
                                    .getUserSession(request);
                            pstmt.setString(1, delete[0]);
                            pstmt.setString(2, request.getRemoteAddr());
                            pstmt.setString(3, "Delete username " + delete[0]
                                    + " by "
                                    + userSession.getUser().getUsername());
                            pstmt.executeUpdate();

                            int i = 0;
                            while (delete.length > i) {
                                User.Factory.delete(Connector.getInstance().getConnection(), delete[i]);
                                i++;
                            }
                            pstmt.close();
                           conn2.close();
                        } catch (DaoException ex) {
                            logger.error(ex);
                        } catch (NamingException ex) {
                            logger.error(ex);
                        } catch (SQLException ex) {
                            logger.error(ex);
                        } catch (UserNotFoundException ex) {
                            logger.error(ex);
                        } catch (Exception e) {
							e.printStackTrace();
						}
                    request
                            .getRequestDispatcher(
                                    "/pages/admin/user-delete-success.jsp?message=delete")
                            .forward(request, response);
                }
            }
            if (action.equalsIgnoreCase("Deactive")) {
                if (request.getParameterValues("deleted") != null) {
                    String[] delete = (String[]) request
                            .getParameterValues("deleted");
                    for (String str : delete)

                        try {

                            Connection conn2 = Connector.getInstance().getConnection();
                            PreparedStatement pstmt = conn2
                                    .prepareStatement(User.Factory.STMT_INSERT_USER_AUDIT);
                            UserSession userSession = UserSession.Factory
                                    .getUserSession(request);
                            pstmt.setString(1, delete[0]);
                            pstmt.setString(2, request.getRemoteAddr());
                            pstmt.setString(3, "Deactive username " + delete[0]
                                    + " by "
                                    + userSession.getUser().getUsername());
                            pstmt.executeUpdate();

                            int i = 0;
                            while (delete.length > i) {
                                User.Factory.deactive(Connector.getInstance().getConnection(), delete[i]);
                                i++;
                            }
                         conn2.close();
                        } catch (DaoException ex) {
                            ex.printStackTrace();
                          //  logger.error(ex);
                        } catch (NamingException ex) {
                            ex.printStackTrace();
                            //logger.error(ex);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            //logger.error(ex);
                        } catch (UserNotFoundException ex) {
                            ex.printStackTrace();
                           // logger.error(ex);
                        } catch (Exception e) {
							e.printStackTrace();
						}
                    request.getRequestDispatcher(
                            "/pages/admin/user-deactive-success.jsp").forward(
                            request, response);
                }
            }

        } else {
          
            String KindOfsearch = (String) request.getParameter("field");
            String WordOfsearch = (String) request.getParameter("User");
            try {
                List<User> users = User.Factory.listlikeadmin(Connector.getInstance().getConnection(), KindOfsearch, WordOfsearch,userSession1.getUser().getUserId(), 0,
                        10);
                int total_pages = User.Factory.countNumberOfUsersLikeAdmin(
                		Connector.getInstance().getConnection(), KindOfsearch,
                        WordOfsearch,userSession1.getUser().getUserId());
                request.setAttribute("listOfUser", users);
                request.setAttribute("total_pages", Integer
                        .toString((total_pages / 10)
                                + ((total_pages % 10) > 0 ? 1 : 0)));
            } catch (DaoException ex) {
                ex.printStackTrace();
                //logger.error(ex);
            } catch (NamingException ex) {
               // logger.error(ex);
                ex.printStackTrace();
            } catch (SQLException ex) {
                //logger.error(ex);
                ex.printStackTrace();
            } catch (Exception e) {
				e.printStackTrace();
			}
            request.getRequestDispatcher(
                    "/pages/admin/user-search.jsp").forward(request,
                    response);

        }
    }
}