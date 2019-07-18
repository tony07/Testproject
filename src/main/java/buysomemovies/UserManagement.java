package com.hs.osna.buysomemovies;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class UserManagement implements Serializable {

	private static UserManagement um;
	private ArrayList<User> user;
	HashMap<Integer, String> sessionIds;

	public UserManagement() {
		this.user = new ArrayList<>();
		this.sessionIds = new HashMap<>();
	}

	public static UserManagement getInstance() {
		if (um == null) {
			um = new UserManagement();
		}

		return um;
	}

	public void clearUser() {
		this.user = new ArrayList<User>();
	}
	
	/*
	 * Bestimmten User hinzufügen
	 */
	public void addUserToList(User user) {
		this.user.add(user);
	}

	/*
	 * Bestimmten User anfordern
	 */
	public User getUserFromList(String username) {
		for (User u : this.user) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}

	public int register(String username, String password) {

		// Username und Passwort dürfen nicht leer sein
		if (username == null || password == null) {
			return 1;
		} // Username und Passwort dürfen nicht kürzer als 3 bzw. 8 Zeichen sein
		else if (username.length() < 3 && password.length() < 8) {
			return 2;
		} // Username darf nicht kürzer als 3 Zeichen sein
		else if (username.length() < 3 && password.length() >= 8) {
			return 3;
		} // Passwort darf nicht kürzer als 8 Zeichen sein
		else if (username.length() >= 3 && password.length() < 8) {
			return 4;
		} // Username darf nicht schon bereits registriert worden sein
		else if (user.contains(new User(username, password))) {
			return 5;
		} // Erfolgreiche Registrierung
		else {
			user.add(new User(username, password));
			return 0;
		}
	}

	/*
	 * User kann sich einloggen. Nach einem erfolgreichen Login, bekommt er eine
	 * SessionId, mit der er Funktionen des Server nutzen kann
	 */
	public int login(String username, String password) throws RemoteException {
		int sessionId = 0;
		if (username != null && password != null) {
			for (User tmpUser : this.user) {
				if (tmpUser.getUsername().equals(username) && tmpUser.getPassword().equals(password)) {
					sessionId = new Random().nextInt();
					this.sessionIds.put(sessionId, username);
					break;
				} 
			}
		}
		return sessionId;
	}

	/*
	 * Logout - Löscht den User aus der Hashmap-Session. Somit kann er keine
	 * weiteren Funktionen mehr ausführen
	 */
	public boolean logout(int sessionId) {
		if (this.sessionIds.containsKey(sessionId)) {
			this.sessionIds.remove(sessionId);
			System.out.println("Es wurde die Session:" + sessionId + " terminiert");
			return true;
		}
		return false;
	}

	/*
	 * Um Funktionen des Server nutzen zu können, erwarten manche Funktionen eine
	 * gültige Session. Mit Hilfer einer HashMap kann man alle gültigen Session
	 * verwalten.
	 */
	public boolean checkSession(int sessionId, String username) {
		boolean checked = false;
		if (this.sessionIds.get(sessionId) != null && username != null) {
			String tmpUsername = this.sessionIds.get(sessionId);
			if (username.equals(tmpUsername)) {
				checked = true;
			}
		}
		return checked;
	}

}
