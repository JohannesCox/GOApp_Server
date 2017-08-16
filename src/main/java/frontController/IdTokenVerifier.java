package frontController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.auth.openidconnect.IdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 *This class can be used to validate an Id Token.
 */
public class IdTokenVerifier {

	private static final String CLIENT_ID = "424823721089-k0fdei5kbr78ginhq127c0jviesmr1th.apps.googleusercontent.com";
	
	/**
	 * Verifies the given Google-IdToken.
	 * 
	 * @param idTokenString The Google IdToken which should be verified.
	 * @return Returns the UserId if successful, otherwise "".
	 */
	public String verify(String idTokenString) {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
			    .setAudience(Collections.singletonList(CLIENT_ID)).build();

	   	
			try {
				GoogleIdToken idToken = verifier.verify(idTokenString);
				if (idToken != null) {
					  Payload payload = idToken.getPayload();
	
					 return payload.getSubject();

				}
			// if the id token was not valid
			} catch (GeneralSecurityException e) {
				return "";
				
			//if the parameter idTokenString was not an Google IdToken
			} catch (java.lang.IllegalArgumentException e) {
				return "";
				
			//If the Server cannot connect with the Google Server.
			//TODO ErrorHandling
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return "";
	}
}
