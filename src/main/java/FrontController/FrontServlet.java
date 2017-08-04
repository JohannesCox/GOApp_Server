package FrontController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import RequestHandler.Command;


public class FrontServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	
	private String userId;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		//no existing session. IdToken has to be verified.
		if (session == null) {
		    userId = verifyUser(request);
		    if (userId == "") {
		    	sendAuthentificationError(response);
		    	return;
		    }
		    session = request.getSession();
		    session.setAttribute("UserId",userId);
	    
		//
		} else {
			session = request.getSession();
			userId = (String) session.getAttribute("UserId");
		}
		
		RequestDispatcher requestDispatcher = new RequestDispatcher(request);
		Command requestHandler = requestDispatcher.createHandler();
		
		if (requestHandler == null) {
			sendIncorrectRequestError(response);
			return;
		} else {
		
			requestHandler.initialize();
			String responseString = requestHandler.process();	
			sendResponse(response, responseString);
		}
	}
	
	private void sendIncorrectRequestError(HttpServletResponse response) {
		//TODO
	}
	
	private void sendAuthentificationError(HttpServletResponse response) {
		//TODO
	}
	
	private void sendResponse(HttpServletResponse response, String responseString) {
		//TODO
	}
	
	
	private String verifyUser(HttpServletRequest request) {
		String idToken = (String) request.getAttribute("IdToken");
		
		if (idToken == null) {
			return "";
		} else {
			IdTokenVerifier idVerifier = new IdTokenVerifier();
			return idVerifier.verify(idToken);
		}
	}

}
