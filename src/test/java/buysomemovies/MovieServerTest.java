package com.hs.osna.buysomemovies;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieServerTest {
	
	private MovieServer movieServer;
	private static UserManagement userManagement = UserManagement.getInstance();
	private static MovieManagement movieManagement = MovieManagement.getInstance();
		
	public MovieServerTest() throws RemoteException {
		this.movieServer = new MovieServer();
	}
	
	@BeforeEach
	public void clear() throws RemoteException {
		userManagement.clearUser();
		movieManagement.clearMovies();
		movieManagement.clearAllBoughtMovies();
	}
	
	@Test
	@DisplayName("Register To MovieServer")
	public void registerToMovieServer() throws RemoteException {
		String username = "Hallo";
		String password = "12345678";
		int returnCodeActual = this.movieServer.register(username, password);
		int returnCodeExcept = 0;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
	}
	
	@Test
	@DisplayName("Login To MovieServer")
	public void loginToMovieServer() throws RemoteException {
		String username = "Hallo";
		String password = "12345678";
		int returnCodeActual = this.movieServer.register(username, password);
		int returnCodeExcept = 0;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
		int sessionActual = this.movieServer.login(username, password);
		Assertions.assertNotEquals(0, sessionActual);
	}
	
	@Test
	@DisplayName("Logout From MovieServer")
	public void logoutFromMovieServer() throws RemoteException {
		String username = "Hallo";
		String password = "12345678";
		int returnCodeActual = this.movieServer.register(username, password);
		int returnCodeExcept = 0;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
		int sessionActual = this.movieServer.login(username, password);
		Assertions.assertNotEquals(0, sessionActual);
		boolean logout = this.movieServer.logout(sessionActual);
		Assertions.assertTrue(logout);
	}
	
	@Test
	@DisplayName("Buy Movie from MovieServer with valid Session")
	public void buyMovieFromServerWithValidSession() throws RemoteException {
		String username = "Hallo";
		String password = "12345678";
		int returnCodeActual = this.movieServer.register(username, password);
		int returnCodeExcept = 0;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
		int sessionActual = this.movieServer.login(username, password);
		boolean checkSession = userManagement.checkSession(sessionActual, username);
		Assertions.assertTrue(checkSession);
		Assertions.assertNotEquals(0, sessionActual);
		Movie movie = new Movie("Casino", 1.0);
		movieManagement.addMovieToList(movie);
		Integer code = this.movieServer.buyMovie(sessionActual, username, movie.getName());
		Assertions.assertNotNull(code);
	}
	
	@Test
	@DisplayName("Buy Movie from MovieServer with no valid Session")
	public void buyMovieFromServerWithNoValidSession() throws RemoteException {
		String username = "Hallo";
		String password = "1234567";
		int returnCodeActual = this.movieServer.register(username, password);
		int returnCodeExcept = 4;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
		int sessionActual = this.movieServer.login(username, password);
		boolean checkSession = userManagement.checkSession(sessionActual, username);
		Assertions.assertFalse(checkSession);
		Assertions.assertEquals(0, sessionActual);
		Movie movie = new Movie("Casino", 1.0);
		movieManagement.addMovieToList(movie);
		Integer code = this.movieServer.buyMovie(sessionActual, username, movie.getName());
		Assertions.assertNull(code);
	}
	
	@Test
	@DisplayName("Show Bought Movies")
	public void showBoughtMovies() throws RemoteException {
		String username = "HalloX";
		String password = "12345678";
		int returnCodeActual = this.movieServer.register(username, password);
		int returnCodeExcept = 0;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
		userManagement.getUserFromList(username).setCredit(100);
		int sessionActual = this.movieServer.login(username, password);
		boolean checkSession = userManagement.checkSession(sessionActual, username);
		Assertions.assertTrue(checkSession);
		Assertions.assertNotEquals(0, sessionActual);
		Movie movie = new Movie("Casino", 1.0);
		movieManagement.addMovieToList(movie);
		Integer code = this.movieServer.buyMovie(sessionActual, username, movie.getName());
		Assertions.assertNotNull(code);
		ArrayList<Movie> boughtMovies = this.movieServer.showAllBoughtsMovies(username);
		boolean checkBoughtMovie = boughtMovies.contains(movie);
		Assertions.assertTrue(checkBoughtMovie);
	}
	
	@Test
	@DisplayName("Search For Movie")
	public void searchForMovie() throws RemoteException {
		String username = "HalloX";
		String password = "12345678";
		int returnCodeActual = this.movieServer.register(username, password);
		int returnCodeExcept = 0;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
		int sessionActual = this.movieServer.login(username, password);
		boolean checkSession = userManagement.checkSession(sessionActual, username);
		Assertions.assertTrue(checkSession);
		Assertions.assertNotEquals(0, sessionActual);
		Movie movie = new Movie("Casino", 1.0);
		movieManagement.addMovieToList(movie);
		Movie getMovie = this.movieServer.searchMovie(sessionActual, username, movie.getName());
		Assertions.assertEquals("Casino", getMovie.getName());
	}
	
	@Test
	@DisplayName("Search For Movie with no Session")
	public void searchForMovieWithNoSession() throws RemoteException {
		String username = "HalloX";
		String password = "1234567";
		int returnCodeActual = this.movieServer.register(username, password);
		int returnCodeExcept = 4;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
		int sessionActual = this.movieServer.login(username, password);
		boolean checkSession = userManagement.checkSession(sessionActual, username);
		Assertions.assertFalse(checkSession);
		Assertions.assertEquals(0, sessionActual);
		Movie movie = new Movie("Casino", 1.0);
		movieManagement.addMovieToList(movie);
		Movie getMovie = this.movieServer.searchMovie(sessionActual, username, movie.getName());
		Assertions.assertNull(getMovie);
	}
	
	@Test
	@DisplayName("Show all Movies")
	public void showAllMovies() throws RemoteException {
		String username = "HalloX";
		String password = "12345678";
		int returnCodeActual = this.movieServer.register(username, password);
		int returnCodeExcept = 0;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
		int sessionActual = this.movieServer.login(username, password);
		boolean checkSession = userManagement.checkSession(sessionActual, username);
		Assertions.assertTrue(checkSession);
		Assertions.assertNotEquals(0, sessionActual);
		Movie movie = new Movie("Casino", 1.0);
		movieManagement.addMovieToList(movie);
		Movie movie2 = new Movie("Good Fellas", 1.0);
		movieManagement.addMovieToList(movie2);
		ArrayList<Movie> movies = this.movieServer.showAllMovies(sessionActual, username);
		Assertions.assertEquals("Casino", movies.get(0).getName());
		Assertions.assertEquals("Good Fellas", movies.get(1).getName());
	}
	
	@Test
	@DisplayName("Show all Movies with no Session")
	public void showAllMoviesWithNoSession() throws RemoteException {
		String username = "HalloX";
		String password = "1234567";
		int returnCodeActual = this.movieServer.register(username, password);
		int returnCodeExcept = 4;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
		int sessionActual = this.movieServer.login(username, password);
		boolean checkSession = userManagement.checkSession(sessionActual, username);
		Assertions.assertFalse(checkSession);
		Assertions.assertEquals(0, sessionActual);
		Movie movie = new Movie("Casino", 1.0);
		movieManagement.addMovieToList(movie);
		Movie movie2 = new Movie("Good Fellas", 1.0);
		movieManagement.addMovieToList(movie2);
		ArrayList<Movie> movies = this.movieServer.showAllMovies(sessionActual, username);
		Assertions.assertNull(movies);
	}
}
