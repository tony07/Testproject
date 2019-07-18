package main.java.com.hs.osna.buysomemovies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MovieManagement implements Serializable {

	private static MovieManagement mm;
	private ArrayList<Movie> movies;
	private HashMap<String, ArrayList<Movie>> boughtMoviesByUser;

	private MovieManagement() {
		this.movies = new ArrayList<>();
		this.boughtMoviesByUser = new HashMap<>();
	}

	public static MovieManagement getInstance() {
		if (mm == null) {
			mm = new MovieManagement();
		}
		return mm;
	}

	public void clearMovies() {
		this.movies = new ArrayList<Movie>();
	}
	
	public void clearAllBoughtMovies() {
		this.boughtMoviesByUser = new HashMap();
	}
	
	/*
	 * Für den Admin. Eine Funktion, um die Filmdatenbank zu füllen
	 */
	public void addMovieToList(Movie movie) {
		this.movies.add(movie);
	}

	/*
	 * Die Filmdatenbank nach einem bestimmten Film durchsuchen. 
	 */
	public Movie getMovieFromList(String name) {
		for (Movie m : this.movies) {
			if (m.getName().equals(name)) {
				return m;
			}
		}
		return null;
	}

	/*
	 * Die Filmdatenbank durchsuchen. 
	 * User kann sich einen Einblick in das Angebot verschaffen.
	 */
	public ArrayList<Movie> getAllMovies() {
		return this.movies;
	}

	/*
	 * Mit dieser Funktion kann ein User einen Film kaufen, falls sein Guthaben ausreicht.
	 * Nach einem erfolgreichen Kauf, bekommt der User eine URL, um den Film zu streamen.
	 * returnCode = 1 --> User oder Film nicht gefunden
	 * returnCode = 2 --> Guthaben reicht nicht aus
	 * returnCode = 0 --> Erfolgreich den Film erworben
	 */
	public int buyMovie(String username, String name) {
		int returnCode = 0;
		User user = UserManagement.getInstance().getUserFromList(username);
		Movie movie = MovieManagement.getInstance().getMovieFromList(name);
		ArrayList<Movie> boughtMovies = new ArrayList<>();
		if (user != null && movie != null) {
			if (user.credit >= movie.getPrice()) {
				ArrayList<Movie> tmpBoughtMovies = this.boughtMoviesByUser.get(username);
				movie.setStreamURL("www.buysomemoviesproject.com/"+username+"-"+name+"-"+new Random().nextInt());
				if (tmpBoughtMovies != null) {
					//boughtMovies = tmpBoughtMovies;
					if (!tmpBoughtMovies.contains(movie)) {
						user.credit -= movie.getPrice();
						tmpBoughtMovies.add(movie);
						this.boughtMoviesByUser.put(username, tmpBoughtMovies);
					}
				} 
				else {
					user.credit -= movie.getPrice();
					boughtMovies.add(movie);
					this.boughtMoviesByUser.put(username, boughtMovies);
				}
			} else {
				returnCode = 2;
			}
		} else {
			returnCode = 1;
		}

//		for (Movie m : this.boughtMoviesByUser.get(username)) {
//			System.out.println("Gekaufte Filme von: " + username + " sind: " + m.getName());
//			System.out.println("Stream URL: " + m.getStreamURL());
//			System.out.println("Übrige Kohle von" + user.getUsername() + " beträgt: " + user.getCredit());
//		}
		
		return returnCode;
	}
	
	/*
	 * Alle gekaufte Filme eines Users können hier angefordert werden.
	 */
	public ArrayList<Movie> showAllBoughtsMovies(String username){
		return this.boughtMoviesByUser.get(username);
	}
}
