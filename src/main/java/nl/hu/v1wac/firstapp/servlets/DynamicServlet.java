package nl.hu.v1wac.firstapp.servlets;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = "/DynamicServlet.do")
public class DynamicServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		int firstNumber = Integer.parseInt(req.getParameter("firstNumber"));
		int secondNumber = Integer.parseInt(req.getParameter("secondNumber"));
		
		String submit = req.getParameter("submit");
		String answer;
		if (submit.equals("+")) {
			answer = Integer.toString(firstNumber + secondNumber);
		} else if (submit.equals("-")) {
			answer = Integer.toString(firstNumber - secondNumber);
		} else if (submit.equals("*")) {
			answer = Integer.toString(firstNumber * secondNumber);
		} else if (secondNumber != 0){
			answer = Integer.toString(firstNumber / secondNumber);
		} else {
			answer = "Delen door 0 is niet mogelijk!";
		}

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println(" <title>Dynamic Example</title>");
		out.println(" <body>");
		out.println(" <h2>Dynamic webapplication example</h2>");
		out.println(answer + "");
		out.println(" </body>");
		out.println("</html>");
	}
}