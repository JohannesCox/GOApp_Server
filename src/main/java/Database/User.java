package Database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "User")
class User {
	@Id @GeneratedValue
	@Column(name="userID")
private String userID;
	@Column(name="username")
private String username;
	public User(String userID, String username) {
		this.userID = userID;
		this.username = username;
	}
	public String getUserID() {
	return userID;
}

public void setUserID(String userID) {
	this.userID = userID;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

	public User() {
		
	}

}
