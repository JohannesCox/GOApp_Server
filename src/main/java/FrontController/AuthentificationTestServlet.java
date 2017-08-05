package FrontController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AuthentificationTestServlet")
public class AuthentificationTestServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
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
		    	response.getWriter().write("No session and no valid IdToken!");
			   	response.getWriter().flush();
			   	response.getWriter().close();
			   	return;
		    } else {
			    session = request.getSession();
			    session.setAttribute("UserId",userId);
			    response.getWriter().write("Authentificated with IdToken. UserId is: " + userId);
			   	response.getWriter().flush();
			   	response.getWriter().close();
		    }
	    
		//
		} else {
			session = request.getSession();
			userId = (String) session.getAttribute("UserId");
			response.getWriter().write("Authentificated with session! UserId is:" + userId);
		   	response.getWriter().flush();
		   	response.getWriter().close();
		}
		
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
