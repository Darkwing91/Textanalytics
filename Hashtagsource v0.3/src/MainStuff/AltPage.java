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
@WebServlet("/AltPage")
public class AltPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AltPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		RequestDispatcher view = request.getRequestDispatcher("/hashtagsource.html");
		view.forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
