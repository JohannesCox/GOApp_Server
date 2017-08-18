package frontController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import requestHandler.RequestDispatcher;
import requestHandler.commands.Command;

/**
 * This class is the only entry point to the server. All requests have to be send to this servlet.
 * 
 */

@WebServlet("/FrontServlet")
public class FrontServlet extends HttpServlet{
	
	
	private static final long serialVersionUID = 1L;
	
	//the maximum time in seconds between two requests before the session expires.
	private static final int MAXSESSIONINTERVALL = 1800;
	
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
		    	sendAuthentErrorResponse(response);
		    	return;
		    }
		    session = request.getSession();
		    session.setAttribute("UserId",userId);
		    session.setMaxInactiveInterval(MAXSESSIONINTERVALL);
	    
		// there is an existing session with the user. The userId can be taken from the session.
		// there is no need to verify the IdToken
		} else {
			if (session.getAttribute("UserId") == null) {
				sendAuthentErrorResponse(response);
				return;
			} else {
				session = request.getSession();
				userId = (String) session.getAttribute("UserId");
			}
		}
		
		RequestDispatcher requestDispatcher = new RequestDispatcher(request,userId);
		Command requestHandler = requestDispatcher.createHandler();
		
		if (requestHandler == null) {
			sendIncorrectRequestErrorResponse(response);
			return;
		} else {
		
			String responseString = requestHandler.process();	
			sendSuccessResponse(response, responseString);
			return;
		}
	}
	
	private void sendSuccessResponse(HttpServletResponse response, String responseString) {
		try {
			response.getWriter().write(responseString);
			response.getWriter().flush();
		   	response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   	
	}
	
	private void sendAuthentErrorResponse(HttpServletResponse response) {
		try {
			response.setStatus(401);
			JsonObject jo = new JsonObject();
			jo.addProperty("successful", "false");
			jo.addProperty("error", "Authentificationerror");
			response.getWriter().write(jo.toString());
			response.getWriter().flush();
		   	response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   	
	}
	
	private void sendIncorrectRequestErrorResponse(HttpServletResponse response) {
		try {
			response.setStatus(400);
			JsonObject jo = new JsonObject();
			jo.addProperty("successful", "false");
			jo.addProperty("error", "InvalidRequestError");	
			response.getWriter().write(jo.toString());
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
