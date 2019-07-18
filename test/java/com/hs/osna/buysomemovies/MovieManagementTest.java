package java.com.hs.osna.buysomemovies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MovieManagementTest {

	private static MovieManagement movieManagement = MovieManagement.getInstance();
	private static UserManagement userManagement = UserManagement.getInstance();
	
	@BeforeEach
	public void clearMovies() {
		movieManagement.clearMovies();
		movieManagement.clearAllBoughtMovies();
		userManagement.clearUser();
	}
	
	@Test
	@DisplayName("Add Movie to MovieManagement")
	public void addMovieToMovieManagement() {
		Movie movie = new Movie("Casino", 1);
		movieManagement.addMovieToList(movie);
		Movie tmpMovie = movieManagement.getMovieFromList(movie.getName());
		String name = "Casino";
		Assertions.assertEquals(name, tmpMovie.getName());
	}
	
	@Test
	@DisplayName("Get Movie which doesnt exists from MovieManagement")
	public void getNoMovieFromMovieManagement() {
		Movie movie = new Movie("Godfather 3", 1);
		Movie tmpMovie = movieManagement.getMovieFromList(movie.getName());
		Assertions.assertNull(tmpMovie);
	}
	
	@Test
	@DisplayName("Get All Movies")
	public void getAllMoviesFromMovieManagement() {
		Movie movie = new Movie("Godfather 1", 1);
		movieManagement.addMovieToList(movie);
		Movie movie2 = new Movie("Godfather 2", 1);
		movieManagement.addMovieToList(movie2);
		ArrayList<Movie> movies = movieManagement.getAllMovies();
		Assertions.assertNotNull(movies);
	}
	
	@Test
	@DisplayName("Buy Movie with no Money")
	public void buyMovieWithNoMoney() {
		Movie movie = new Movie("Godfather 1", 1);
		movieManagement.addMovieToList(movie);
		User user = new User("Hallo", "12345678");
		user.setCredit(0.5);
		userManagement.addUserToList(user);
		int returnCodeActual = movieManagement.buyMovie(user.getUsername(), movie.getName());
		int returnCodeExcept = 2;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
	}
	
	@Test
	@DisplayName("Buy Movie with User and no Movie")
	public void buyMovieWithNoUserAndNoMovie() {
		Movie movie = new Movie("GodfatherX 1", 1);
		User user = new User("HalloX", "12345678");
		int returnCodeActual = movieManagement.buyMovie(user.getUsername(), movie.getName());
		int returnCodeExcept = 1;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
	}
	
	@Test
	@DisplayName("Buy Movie And Reduce Credit")
	public void buyMovieAndReduceCredit() {
		Movie movie = new Movie("Godfather 1", 1.0);
		movieManagement.addMovieToList(movie);
		User user = new User("HalloZ", "12345678");
		user.setCredit(125.5);
		userManagement.addUserToList(user);
		int returnCodeActual = movieManagement.buyMovie(user.getUsername(), movie.getName());
		double newCreditExcept = 124.5;
		double newCreditActual = userManagement.getUserFromList(user.getUsername()).getCredit();
		Assertions.assertEquals(newCreditExcept, newCreditActual);
		int returnCodeExcept = 0;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
		boolean checkIfMovieWasBought = movieManagement.showAllBoughtsMovies(user.getUsername()).contains(movie);
		Assertions.assertTrue(checkIfMovieWasBought);
	}
	
	@Test
	@DisplayName("Buy A Other Movie")
	public void buyAOtherMovie() {
		Movie movie = new Movie("Godfather 1", 1.0);
		movieManagement.addMovieToList(movie);
		Movie movie2 = new Movie("Godfather 2", 1.0);
		movieManagement.addMovieToList(movie2);
		User user = new User("HalloZ", "12345678");
		user.setCredit(125.5);
		userManagement.addUserToList(user);
		int returnCodeActual = movieManagement.buyMovie(user.getUsername(), movie.getName());
		double newCreditExcept = 124.5;
		double newCreditActual = userManagement.getUserFromList(user.getUsername()).getCredit();
		Assertions.assertEquals(newCreditExcept, newCreditActual);
		int returnCodeExcept = 0;
		Assertions.assertEquals(returnCodeExcept, returnCodeActual);
		
		boolean checkIfMovieWasBought = movieManagement.showAllBoughtsMovies(user.getUsername()).contains(movie);
		Assertions.assertTrue(checkIfMovieWasBought);
		
		int returnCodeActual2 = movieManagement.buyMovie(user.getUsername(), movie2.getName());
		double newCreditExcept2 = 123.5;
		double newCreditActual2 = userManagement.getUserFromList(user.getUsername()).getCredit();
		Assertions.assertEquals(newCreditExcept2, newCreditActual2);
		int returnCodeExcept2 = 0;
		Assertions.assertEquals(returnCodeExcept2, returnCodeActual2);
		
		boolean checkIfMovieWasBought2 = movieManagement.showAllBoughtsMovies(user.getUsername()).contains(movie2);
		Assertions.assertTrue(checkIfMovieWasBought2);
		
	}
}
