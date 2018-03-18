package MainStuff;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Servlet implementation class MeinErstesServlet
 */
@WebServlet("/HalloServlet")
public class MeinErstesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MeinErstesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set response headers
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				
				// create HTML form
				PrintWriter writer = response.getWriter();
				writer.append("<!DOCTYPE html>\r\n")
					  .append("<html>\r\n")
					  .append("		<head>\r\n")
					  .append("			<title>Form input</title>\r\n")
					  .append("		</head>\r\n")
					  .append("		<body>\r\n")
					  .append("			<form action=\"HalloServlet\" method=\"POST\">\r\n")
					  .append("				Enter your name: \r\n")
					  .append("				<input type=\"text\" name=\"user\" />\r\n")
					  .append("				<input type=\"submit\" value=\"Submit\" />\r\n")
					  .append("			</form>\r\n")
					  .append("		</body>\r\n")
					  .append("</html>\r\n");
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		
		response.setContentType("image/png");
		
		OutputStream outputStream = response.getOutputStream();

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Croatia", 22);
        dataset.setValue("Bohemia", 34);
        dataset.setValue("Bulgaria", 18);
        dataset.setValue("Spain", 5);
        dataset.setValue("Others", 21);

        JFreeChart chart = ChartFactory.createPieChart("Popular destinations", 
                dataset, true, false, false);

        chart.setBorderVisible(false);
        
        int width = 500;
        int height = 350;
        
        ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
	}

}
