package MainStuff;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
 * Servlet implementation class Analyse
 */
@WebServlet("/Analyse")
public class Analyse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Analyse() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream outputStream = response.getOutputStream();
		String id = (String) request.getAttribute("id");
		List<User> users = new ArrayList<User>();
		TwitterStuff twitterAcc = new TwitterStuff();
		users = twitterAcc.getTweets("" + id);
		for(User user : users){
			user.SetNachichten(twitterAcc.getMsgs(user.getName()));
		}

        response.setContentType("image/png");

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
