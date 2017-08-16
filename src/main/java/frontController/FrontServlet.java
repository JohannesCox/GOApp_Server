package frontController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import RequestHandler.RequestDispatcher;
import RequestHandler.Commands.Command;

/**
 * This class is the only entry point to the server. All requests have to be send to this servlet.
 * 
 */

@WebServlet("/FrontServlet")
public class FrontServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("I am working!");
	   	response.getWriter().flush();
	   	response.getWriter().close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		//no existing session. IdToken has to be verified.
		if (session == null) {
		    userId = verifyUser(request);
		    if (userId == "") {
		    	sendResponse(response, getAuthentificationError());
		    	return;
		    }
		    session = request.getSession();
		    session.setAttribute("UserId",userId);
	    
		// there is an existing session with the user. The userId can be taken from the session.
		} else {
			session = request.getSession();
			userId = (String) session.getAttribute("UserId");
		}
		
		RequestDispatcher requestDispatcher = new RequestDispatcher(request,userId);
		Command requestHandler = requestDispatcher.createHandler();
		
		if (requestHandler == null) {
			sendResponse(response, getIncorrectRequestError());
			return;
		} else {
		
			String responseString = requestHandler.process();	
			sendResponse(response, responseString);
		}
	}
	
	private String getIncorrectRequestError() {
		JsonObject jo = new JsonObject();
		jo.addProperty("successful", "false");
		jo.addProperty("error", "InvalidRequestError");	
		return jo.toString();
	}
	
	private String getAuthentificationError() {
		JsonObject jo = new JsonObject();
		jo.addProperty("successful", "false");
		jo.addProperty("error", "Authentificationerror");
		return jo.toString();
	}
	
	private void sendResponse(HttpServletResponse response, String responseString) {
		try {
			response.getWriter().write(responseString);
			response.getWriter().flush();
		   	response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   	
	}
	
	
	private String verifyUser(HttpServletRequest request) {
		String idToken = request.getParameter("IdToken");
		
		if (idToken == null) {
			return "";
		} else {
			IdTokenVerifier idVerifier = new IdTokenVerifier();
			return idVerifier.verify(idToken);
		}
	}

}
