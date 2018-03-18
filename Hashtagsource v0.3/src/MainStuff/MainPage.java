package MainStuff;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import DataStuff.SimpleClassifier;

/**
 * Servlet implementation class MainPage
 */
@WebServlet("/MainPage")
public class MainPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		// create HTML form
		PrintWriter writer = response.getWriter();
		writer.append("<!DOCTYPE html>\r\n")
			  .append("<html>\r\n")
			  .append("		<head>\r\n")
			  .append("			<title>Hashtag</title>\r\n")
			  .append("		</head>\r\n")
			  .append("		<body>\r\n")
			  .append("			<form action=\"MainPage\" method=\"POST\">\r\n")
			  .append("				Hashtag: \r\n")
			  .append("				<input type=\"text\" name=\"hashtag\" />\r\n")
			  .append("				<input type=\"submit\" value=\"Analyse\" />\r\n")
			  .append("			</form>\r\n")
			  .append("		</body>\r\n")
			  .append("</html>\r\n");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream outputStream = response.getOutputStream();
		String id = request.getParameter("hashtag");
		response.setContentType("image/png");
		SimpleClassifier sc = new SimpleClassifier();
		if(!id.substring(0,1).equals("#"))
			id = "#" + id;
		
		List<User> users = new ArrayList<User>();
			
		//Crawl Users
		TwitterStuff twitterAcc = new TwitterStuff();
		users = twitterAcc.getTweets("" + id);
		for(User user : users){
			user.SetNachichten(twitterAcc.getMsgs(user.getName()));
			user.SetGender(sc.classifyUserGender(user));
			if(user.getGender().equals("channel"))
				user.SetAlter(0);
			else
				user.SetAlter(sc.classifyUserAge(user));
		}
	        
	    //Create Gender/Age Chart
		Hashtable<String,Integer> genderlist = getUserGenderChart(users);
		Hashtable<String,Integer> agelist = getUserAgeChart(users);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Enumeration<String> genders = genderlist.keys();
		Enumeration<String> ages = agelist.keys();
		while(genders.hasMoreElements()) {
			String key = (String) genders.nextElement();
			dataset.addValue(genderlist.get(key).intValue(), key, "Gender");
			System.out.println("Key: " + key + " Value: " + genderlist.get(key).intValue());
		}
		while(ages.hasMoreElements()) {
			String key = (String) ages.nextElement();
			dataset.addValue(agelist.get(key).intValue(), key, "Age");
			System.out.println("Key: " + key + " Value: " + agelist.get(key).intValue());
		}
		//PieSectionLabelGenerator genderLabelGenerator = new StandardPieSectionLabelGenerator("{0} = {2}");
	    JFreeChart chart2 = ChartFactory.createBarChart(
	        	("Hashtag: " + id + " Analysed users: " + users.size()), 
	            "Category",
	            "Score",
	            dataset, 
	            PlotOrientation.VERTICAL,
	            true, true, false);
	    //BarPlot plot2 = (BarPlot) chart2.getPlot();
	    //plot2.setLabelGenerator(genderLabelGenerator);
	        
	    ChartUtilities.writeChartAsPNG(outputStream, chart2, 750, 750);
		
	}
	
	private Hashtable<String, Integer> getUserAgeChart(List<User> users){
		Hashtable<String,Integer> agelist = new Hashtable<String,Integer>();
		int i = 0, j = 0, k = 0, l = 0, m = 0, n = 0;
		
		for(User user : users) {
			int p = user.getAlter();
			if(p < 18){
				i++;
			}
			if(p >= 18 && p < 25){
				j++;
			}
			if(p >= 25 && p < 35){
				k++;
			}
			if(p >= 35 && p < 45){
				l++;
			}
			if(p >= 45){
				m++;
			}
			if(p == 0){ //unclassified
				n++;
			}
		}
		agelist.put("unter 18", new Integer(i));
		agelist.put("18 - 24", new Integer(j));
		agelist.put("25 - 34", new Integer(k));
		agelist.put("35 - 44", new Integer(l));
		agelist.put("> 45", new Integer(m));
		agelist.put("unclassified", new Integer(n));
		
		return agelist;
	}
	
	private Hashtable<String, Integer> getUserGenderChart(List<User> users){
		Hashtable<String,Integer> genderlist = new Hashtable<String,Integer>();
		int i = 0, j = 0, k = 0;
		
		for(User user : users) {
			String p = user.getGender();
			if(p.equals("female")){
				i++;
			}
			if(p.equals("male")){
				j++;
			}
			if(p.equals("channel")){
				k++;
			}
		}
		genderlist.put("female", new Integer(i));
		genderlist.put("male", new Integer(j));
		genderlist.put("bot", new Integer(k));
		
		return genderlist;
	}
	
}
