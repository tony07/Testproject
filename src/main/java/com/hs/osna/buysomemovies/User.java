package main.java.com.hs.osna.buysomemovies;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

	private String username, password;
	double credit;

	public User() {
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.credit = 0;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	@Override
	public boolean equals(Object o) {
		
		if (o == null)
			return false;
		if (o == this)
			return true;
		User user = (User) o;
		/*if (getClass() != o.getClass())
			return false;*/
		if (Objects.equals(user.getUsername(), this.getUsername()))
			return true;
		else
			return false;

	}
}
