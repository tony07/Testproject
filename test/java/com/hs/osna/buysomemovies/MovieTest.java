package java.com.hs.osna.buysomemovies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MovieTest {

	@Test
	@DisplayName("Check Movie Complete")
	public void checkMovieComplete() {
		Movie movie = new Movie();
		movie.setFilmId(1);
		Assertions.assertEquals(1, movie.getFilmId());
		movie.setName("Casino");
		Assertions.assertEquals("Casino", movie.getName());
		movie.setPrice(1);
		Assertions.assertEquals(1, movie.getPrice());
		movie.setStreamURL("www.buysomemovies.com");
		Assertions.assertEquals("www.buysomemovies.com", movie.getStreamURL());
		boolean checkNull = movie.equals(null);
		Assertions.assertFalse(checkNull);
		boolean identical = movie.equals(movie);
		Assertions.assertTrue(identical);
		boolean sameMovie = movie.equals(new Movie("Casino", 1.0));
		Assertions.assertTrue(sameMovie);
		boolean notSameMovie = movie.equals(new Movie("Heat",1.0));
		Assertions.assertFalse(notSameMovie);
		}
	
}
