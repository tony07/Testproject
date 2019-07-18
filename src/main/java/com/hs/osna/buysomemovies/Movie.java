package main.java.com.hs.osna.buysomemovies;

import java.io.Serializable;
import java.util.Objects;

public class Movie implements Serializable {

	private static int filmIdCounter = 0;
	private int filmId;
	private String name;
	private double price;
	private String streamURL;
	
	public Movie() {
	}
	
	public Movie(String name, double price) {
		Movie.filmIdCounter++;
		this.filmId = Movie.filmIdCounter;
		this.name = name;
		this.price = price;
	}
	
	public int getFilmId() {
		return filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public String getStreamURL() {
		return streamURL;
	}

	public void setStreamURL(String streamURL) {
		this.streamURL = streamURL;
	}

	@Override
	public boolean equals(Object o) {

		if (o == null)
			return false;
		if (o == this)
			return true;
		Movie movie = (Movie) o;
		/*if (getClass() != o.getClass())
			return false;*/
		if (Objects.equals(movie.getName(), this.getName()))
			return true;
		else
			return false;

	}
	/*
	@Override
	public String toString() {
		return "FilmId: "+this.filmId+ " Movie: "+this.name+" Price: "+this.price+" Stream-URL: "+this.streamURL;
	}*/
	
}

