package main.java.com.hs.osna.buysomemovies;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class MovieServer extends UnicastRemoteObject implements IMovieServer{

	private static UserManagement userManagement = UserManagement.getInstance();
	private static MovieManagement movieManagement = MovieManagement.getInstance();
	
	public MovieServer() throws RemoteException {
		System.out.println("MovieServer created...");
	}

	private static void generateAccounts() {
		User user1 = new User("Hallo", "123456789");
		user1.setCredit(0.5);
		userManagement.addUserToList(user1);
		
		User user2 = new User("Hallo2", "123456789");
		user2.setCredit(10);
		userManagement.addUserToList(user2);
	}
	
	private static void generateMovies() {
		Movie movie1 = new Movie("Casino", 1.00);
		movieManagement.addMovieToList(movie1);
		
		Movie movie2 = new Movie("Good Fellas", 1.00);
		movieManagement.addMovieToList(movie2);
	}
	
	/*
	 * Die Main-Funktion sorgt dafür, dass der Server gestartet wird.
	 * Zustzälich erzeugt der Server Testdaten
	 */
    public static void main(String[] args) {

        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Naming.rebind("RentAMovie", new MovieServer());
            System.out.println("Movie server registered");
            
            generateAccounts();
            generateMovies();
            
        } catch (Exception e) {
            System.err.println("Exception during server registration (port = " + Registry.REGISTRY_PORT + ")");
            e.printStackTrace();
        }

    }
	
    /*
     * Jeder Client kann sich hier registrieren. Username und Passwort-Richtlinien werden im Usermanagement verwaltet.
     */
    @Override
	public int register(String username, String password) throws RemoteException {
		int code = userManagement.register(username, password);
		return code;
	}

	/*
	 * Auf Anfragen vom Clienten wird hier reagiert. Die Klasse Usermanagement sorgt für eine gültige Session
	 */
	@Override
	public int login(String username, String password) throws RemoteException {
		int sessionId = userManagement.login(username, password);
		return sessionId;	
	}

	/*
	 * Logout kann sich jeder Nutzer nur, wenn er eine gültige Session besitzt
	 */
	@Override
	public boolean logout(int sessionId) throws RemoteException {
		boolean logout = userManagement.logout(sessionId);
		return logout;	
	}

	/*
	 * Falls der Client den Filmkatalog anfragen will, braucht er eine gültige Session und einen Usernamen
	 */
	@Override
	public ArrayList<Movie> showAllMovies(int sessionId, String username){
		if(userManagement.checkSession(sessionId, username)) {
			ArrayList<Movie> tmpMovies = movieManagement.getAllMovies();
			return tmpMovies;
		}else {
			return null;
		}
	}
	
	/*
	 * Falls der Client nach einem bestimmten Film suchen möchte.
	 */
	@Override
	public Movie searchMovie(int sessionId, String username, String name) {
		if(userManagement.checkSession(sessionId, username)) {
			return movieManagement.getMovieFromList(name);
		}else {
			return null;
		}
	}
	
	/*
	 * Der Kauf eines Filmes durch den Client.
	 */
	@Override
	public Integer buyMovie(int sessionId, String username, String name) {
		if(userManagement.checkSession(sessionId, username)) {
			return movieManagement.buyMovie(username, name);
		} else {
			return null;
		}
	}
	
	@Override
	public ArrayList<Movie> showAllBoughtsMovies(String username) throws RemoteException {
		return movieManagement.showAllBoughtsMovies(username);
	}
}
