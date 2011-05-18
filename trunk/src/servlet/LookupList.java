package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import web.LookupQuery;
import database.Connector;
import exception.DaoException;

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
		String title = request.getParameter("title");
		String tableTitle = request.getParameter("tableTitle");
		String showFields = request.getParameter("showFields"); 
		String itemName = request.getParameter("itemName");		
		String queryData = (String)request.getParameter("queryData");		
		String page = (String) request.getParameter("page");
		String parameter = request.getParameter("parameters"); 
		String[] parameters = null; 
		if(parameter!=null && !parameter.equals(""))
			parameters = ((String)parameter).split(",");					
		if (page != null) {
			int KindOfsearch = Integer.parseInt((String) request.getParameter("KindOfsearch"));
			String WordOfsearch = (String) request.getParameter("WordOfsearch");
			try {
				int total_pages = LookupQuery.countNumberLike(
						Connector.getInstance().getConnectionAdmin(), KindOfsearch,
						WordOfsearch,queryData,parameters);
				try {
					List<ArrayList<String>> datas = LookupQuery
							.listlike(Connector.getInstance().getConnectionAdmin(),
									KindOfsearch, WordOfsearch,
									10 * (Integer.parseInt(page) - 1), 10,queryData,showFields,parameters);
					request.setAttribute("listOfUser", datas);
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

		String destination = "/pages/list.jsp?title="+title.replaceAll(" ","%20")+"&tableTitle="+tableTitle.replaceAll(" ","%20")+"&itemName="+itemName.replaceAll(" ","%20")+"&showFields="+showFields.replaceAll(" ","%20")+"&queryData="+queryData.replaceAll(" ","%20");
		if(parameter!=null && !parameter.equals(""))
			destination+="&parameters="+parameter.replaceAll(" ","%20");
		request.getRequestDispatcher(destination).forward(request,
				response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {		

		String title = (String)request.getParameter("title");
		String tableTitle = (String)request.getParameter("tableTitle");
		String showFields = (String)request.getParameter("showFields"); 
		String itemName = (String)request.getParameter("itemName");				
		String queryData = (String)request.getParameter("queryData");
		String parameter = request.getParameter("parameters");		
		String[] parameters = null; 
		if(parameter!=null && !parameter.equals(""))
			parameters = ((String)parameter).split(",");
		if (request.getParameter("Action") != null) {
			String action = request.getParameter("Action");
			if (action.equalsIgnoreCase("Search")) {
				int KindOfsearch = Integer.parseInt((String) request.getParameter("field"));
				String WordOfsearch = (String) request.getParameter("Value");
				try {
					List<ArrayList<String>> datas = LookupQuery
					.listlike(Connector.getInstance().getConnectionAdmin(),
							KindOfsearch, WordOfsearch, 0, 10, queryData,showFields,parameters);
					int total_pages = LookupQuery.countNumberLike(
							Connector.getInstance().getConnectionAdmin(), KindOfsearch,
							WordOfsearch,queryData,parameters);
					request.setAttribute("listOfUser", datas);
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
				String destination = "/pages/list.jsp?KindOfsearch=" + KindOfsearch
				+ "&WordOfsearch=" + WordOfsearch+"&title="+title.replaceAll(" ","%20")+"&tableTitle="+tableTitle.replaceAll(" ","%20")+"&itemName="+itemName.replaceAll(" ","%20")+"&showFields="+showFields.replaceAll(" ","%20")+"&queryData="+queryData.replaceAll(" ","%20");
				if(parameter!=null && !parameter.equals(""))
					destination+="&parameters="+parameter.replaceAll(" ","%20");
				request.getRequestDispatcher(destination).forward(request, response);
			}
		}
	}

}
