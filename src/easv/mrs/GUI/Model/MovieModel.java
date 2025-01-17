package easv.mrs.GUI.Model;

import easv.mrs.BE.Movie;
import easv.mrs.BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class MovieModel {

    private ObservableList<Movie> moviesToBeViewed;

    private MovieManager movieManager;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }



    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }

    public void createNewMovie(Movie newMovie) throws Exception {
            Movie m = movieManager.createNewMovie(newMovie);
            moviesToBeViewed.add(m);
    }

    public void deleteMovie(Movie selectedMovie) throws Exception {
            movieManager.deleteMovie(selectedMovie);
            moviesToBeViewed.remove(selectedMovie);
    }

    public void updateMovie(Movie selectedMovie) throws Exception {
        movieManager.updateMovie(selectedMovie);
        /*
        Movie m = moviesToBeViewed.get(moviesToBeViewed.indexOf(selectedMovie));
        m.setTitle(selectedMovie.getTitle());
        m.setYear(selectedMovie.getYear());
        */
    }
}
