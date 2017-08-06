package FrontController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import RequestHandler.Command;
import RequestHandler.RequestDispatcher;

/**
 * For Testing only! Doesnt need any authentification.
 * 
 * TODO delete
 */

@WebServlet("/RequestTestServlet")
public class RequestTestServlet extends HttpServlet {
	
	private static final long serialVersionUID = 3L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("I am working!");
	   	response.getWriter().flush();
	   	response.getWriter().close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = request.getParameter("UserId");
		
		if (userId == null) {
			response.getWriter().write("Add a random userId @\"UserId\"!");
		   	response.getWriter().flush();
		   	response.getWriter().close();
		}
		
		RequestDispatcher requestDispatcher = new RequestDispatcher(request,userId);
		Command requestHandler = requestDispatcher.createHandler();
		
		if (requestHandler == null) {
			response.getWriter().write("Invalid Request!");
		   	response.getWriter().flush();
		   	response.getWriter().close();
		} else {
		
			String responseString = requestHandler.process();	
			response.getWriter().write(responseString);
		   	response.getWriter().flush();
		   	response.getWriter().close();
		   	
		}
		
	}
	
}
