package com.hs.osna.buysomemovies;

import java.rmi.RemoteException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hs.osna.buysomemovies.User;
import com.hs.osna.buysomemovies.UserManagement;

public class UserManagementTest {

	private static UserManagement userManagement = UserManagement.getInstance();
	
	@BeforeEach
	public void clearUser() {
		userManagement.clearUser();
	}
	
	@Test
	@DisplayName("Correct Credentials")
	public void registerWithCorrectCredentials() {
		String username = "Test";
		String password = "12345678";
		User user = new User(username, password);
		
		int exceptValue = 0;
		int actualValue = userManagement.register(username, password);
		
		Assertions.assertEquals(exceptValue, actualValue);
	}
	
	@Test
	@DisplayName("Register with no Information")
	public void registerWithNoInformation() {
		String username = null;
		String password = null;
		User user = new User(username, password);
		
		int exceptValue = 1;
		int actualValue = userManagement.register(username, password);
		
		Assertions.assertEquals(exceptValue, actualValue);
	}
	
	@Test
	@DisplayName("Register with Short User Name and Passwort")
	public void registerWithIncorrectInformation() {
		String username = "Te";
		String password = "1234567";
		User user = new User(username, password);
		
		int exceptValue = 2;
		int actualValue = userManagement.register(username, password);
		
		Assertions.assertEquals(exceptValue, actualValue);
	}
	
	@Test
	@DisplayName("Short User Name")
	public void registerWithShortUsernameAndCorrectPassword() {
		String username = "Te";
		String password = "12345678";
		User user = new User(username, password);
		
		int exceptValue = 3;
		int actualValue = userManagement.register(username, password);
		
		Assertions.assertEquals(exceptValue, actualValue);
	}
	
	@Test
	@DisplayName("Short Password")
	public void registerWithCorrectUsernameAndShortPassword() {
		String username = "Test";
		String password = "1234567";
		User user = new User(username, password);
		
		int exceptValue = 4;
		int actualValue = userManagement.register(username, password);
		
		Assertions.assertEquals(exceptValue, actualValue);
	}
	
	@Test
	@DisplayName("User Name does exists")
	public void registerUserWhoAlreadyExists() {
		String username = "Bob";
		String password = "12345678";
		User user = new User(username, password);
		userManagement.register(username, password);
		
		int exceptValue = 5;
		int actualValue = userManagement.register(username, password);
		
		Assertions.assertEquals(exceptValue, actualValue);
	}
	
	@Test
	@DisplayName("Get User from User Management")
	public void getUserFromList() {
		User user = new User("Hallo", "12345678");
		userManagement.addUserToList(user);
		user = userManagement.getUserFromList("Hallo");
		Assertions.assertNotNull(user);
		String username = "Hallo";
		Assertions.assertEquals(username, user.getUsername());
	}

	@Test
	@DisplayName("Get User who doesnt exists from User Management")
	public void getNoUserFromList() {
		User user = userManagement.getUserFromList("Hallo2");
		Assertions.assertNull(user);
	}
	
	@Test
	@DisplayName("Login user who exists")
	public void logInUserFromUserManagement() throws RemoteException {
		User user = new User("Hallo3", "123456789");
		userManagement.addUserToList(user);
		User tmpUser = userManagement.getUserFromList("Hallo3");
		
		Assertions.assertNotNull(tmpUser);
		
		int sessionId = userManagement.login(tmpUser.getUsername(), tmpUser.getPassword());
		Assertions.assertNotEquals(0, sessionId);
	}
	
	@Test
	@DisplayName("Check Session from login user who exists")
	public void checkSessionOfUserFromUserManagement() throws RemoteException {
		User user = new User("Hallo3", "123456789");
		userManagement.addUserToList(user);
		User tmpUser = userManagement.getUserFromList("Hallo3");
		
		Assertions.assertNotNull(tmpUser);
		
		int sessionId = userManagement.login(tmpUser.getUsername(), tmpUser.getPassword());
		Assertions.assertNotEquals(0, sessionId);
		
		boolean actualSession = userManagement.checkSession(sessionId, tmpUser.getUsername());
		Assertions.assertTrue(actualSession);
	}
	
	@Test
	@DisplayName("Check Session from login user who doesnt exists")
	public void checkSessionOfNoUserFromUserManagement() throws RemoteException {
		User user = new User("Hallo4", "123456789");
		User tmpUser = userManagement.getUserFromList(user.getUsername());
		
		Assertions.assertNull(tmpUser);
		
		int actualSessionId = userManagement.login(user.getUsername(), user.getPassword());
		Assertions.assertEquals(0, actualSessionId);
		
		boolean actualSession = userManagement.checkSession(actualSessionId, user.getUsername());
		Assertions.assertFalse(actualSession);
	}
	
	@Test
	@DisplayName("Logout user who exists")
	public void logOutUserFromUserManagement() throws RemoteException {
		User user = new User("Hallo3", "123456789");
		userManagement.addUserToList(user);
		User tmpUser = userManagement.getUserFromList("Hallo3");
		
		Assertions.assertNotNull(tmpUser);
		
		int sessionId = userManagement.login(tmpUser.getUsername(), tmpUser.getPassword());
		Assertions.assertNotEquals(0, sessionId);
		
		boolean logoutActual = userManagement.logout(sessionId);
		Assertions.assertTrue(logoutActual);
	}
	
	@Test
	@DisplayName("Logout user who doesnt exists")
	public void logOutNoUserFromUserManagement() throws RemoteException {
		User user = new User("Hallo4", "123456789");
		User tmpUser = userManagement.getUserFromList(user.getUsername());
		
		Assertions.assertNull(tmpUser);
		
		int sessionId = userManagement.login(user.getUsername(), user.getPassword());
		Assertions.assertEquals(0, sessionId);
		
		boolean logoutActual = userManagement.logout(sessionId);
		Assertions.assertFalse(logoutActual);
	}
}
