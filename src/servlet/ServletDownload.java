package servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Connector;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class ServletDownload extends HttpServlet {

	private static final long serialVersionUID = 7550790776847823268L;

	public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
		
		ServletOutputStream op = null;
		try {			
			
			JasperPrint jasperTemp = JasperFillManager.fillReport(ServletDownload.class.getClassLoader().getResourceAsStream("report1.jasper"),  new HashMap<Object,Object>(), Connector.getInstance().getConnection());			
			byte[] result = JasperExportManager.exportReportToPdf(jasperTemp);
			
			op = response.getOutputStream();			

			response.setHeader("Content-Disposition","inline, filename=myReport.pdf");
			response.setContentType("application/pdf");		
			response.setContentLength(result.length);
			
			op.write(result);
			
			op.flush();
											
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {		
			if(op!=null)
				op.close();
		}			
	}

}
