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
	@Column(name="Email")
private String email;
	
	User(String userID, String username, String email) {
		this.userID = userID;
		this.username = username;
		this.email = email;
	}
	
	User(String userID, String username){
		this.userID = userID;
		this.username = username;
	}
	String getUserID() {
	return userID;
}

void setUserID(String userID) {
	this.userID = userID;
}


String getUsername() {
	return username;
}

void setUsername(String username) {
	this.username = username;
}


String getEmail() {
	return email;
}
void setEmail(String email) {
	this.email = email;
}


}
