package Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

/**
 * Models an User in the database.
 *
 */
@Entity
@Table(appliesTo = "User")
public class User {
	
	@Id
	@Column(name="userID")
private String userID;
	
	@Column(name="username")
private String username;
	@Column(name="notificationID")
private String notificationID;
	User(){}
	
	/**
	 * Creates a user
	 * @param userID of the user
	 * @param username of the user
	 * @param notificationID of the user
	 */
	public User(String userID, String username, String notificationID) {
		this.userID = userID;
		this.username = username;
		this.notificationID = notificationID;
	}
	
	/**
	 * Creates a user. The email will be set to null.
	 * @param userID of the user
	 * @param username of the user
	 */
	public User(String userID, String username){
		this.userID = userID;
		this.username = username;
		this.notificationID = null;
	}

	/**
	 * @return userID of the user.
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID of the user to set.
	 */
	void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return username of the user.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username of the user to set.
	 */
	void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return notificationID of the user.
	 */
	public String getNotificationID() {
		return notificationID;
	}
	
	/**
	 * @param notificationID of the user to set.
	 */
	void setNotificationID(String notificationID) {
		this.notificationID = notificationID;
	}


}
