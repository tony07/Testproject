package main.java.com.hs.osna.buysomemovies;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IMovieServer extends Remote{

	public int register(String username, String password) throws RemoteException;
	public int login(String username, String password) throws RemoteException;
	public boolean logout(int sessionId) throws RemoteException;
	public ArrayList<Movie> showAllMovies(int sessionId, String username) throws RemoteException;
	public Movie searchMovie(int sessionId, String username, String name) throws RemoteException;
	public Integer buyMovie(int sessionId, String username, String name) throws RemoteException;
	public ArrayList<Movie> showAllBoughtsMovies(String username) throws RemoteException;
}
