
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRViewerSimple.java 3031 2009-08-27 11:14:57Z teodord $
 */
public class JRViewerSimple extends JRViewer
{


	/**
	 *
	 */
	public JRViewerSimple(JasperPrint jrPrint) throws JRException
	{
		super(jrPrint);			
	}


}
