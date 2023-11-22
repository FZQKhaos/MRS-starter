package easv.mrs.GUI.Controller;

import easv.mrs.BE.Movie;
import easv.mrs.GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieViewController implements Initializable {


    @FXML
    private TextField txtMovieSearch;

    @FXML
    private ListView<Movie> lstMovies;

    @FXML
    private TableView<Movie> tblMovies;

    @FXML
    private TableColumn<Movie, String> colTitle;

    @FXML
    private TableColumn<Movie, Integer> colYear;

    @FXML
    private TextField txtTitle, txtYear;

    private MovieModel movieModel;

    public MovieViewController()  {

        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // TableView columns + BE getters
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        lstMovies.setItems(movieModel.getObservableMovies());
        tblMovies.setItems(movieModel.getObservableMovies());

        //tblMovies.getSelectionModel().getSelectedItem();
        tblMovies.getSelectionModel().selectedItemProperty().addListener
                ((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        txtTitle.setText(newValue.getTitle());
                        txtYear.setText(String.valueOf(newValue.getYear()));
                    }
        });

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }


    public void onActionCreateNewMovie(ActionEvent actionEvent) {

        String title = txtTitle.getText();
        int year = Integer.parseInt(txtYear.getText());

        Movie newMovie = new Movie(-1, year, title);

        try {
            movieModel.createNewMovie(newMovie);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    public void onActionDeleteMovie(ActionEvent actionEvent) throws Exception {

        Movie selectedMovie = tblMovies.getSelectionModel().getSelectedItem();

        if (selectedMovie != null){
            movieModel.deleteMovie(selectedMovie);
        }

    }

    public void onActionUpdateMovie(ActionEvent event) {
        String title = txtTitle.getText();
        int year = Integer.parseInt(txtYear.getText());
        Movie selectedMovie = tblMovies.getSelectionModel().getSelectedItem();
        try {
            if (selectedMovie != null){
                selectedMovie.setTitle(title);
                selectedMovie.setYear(year);

                //DB Chain
                movieModel.updateMovie(selectedMovie);
                tblMovies.refresh();
                lstMovies.refresh();

                txtTitle.clear();
                txtYear.clear();
            }
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }
}
