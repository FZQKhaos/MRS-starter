package easv.mrs.DAL;

import easv.mrs.BE.Movie;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;

public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";

    public List<Movie> getAllMovies() throws IOException {
        // Read all lines from file
        List<String> lines = Files.readAllLines(Path.of(MOVIES_FILE));
        List<Movie> movies = new ArrayList<>();

        // Parse each line as movie
        for (String line: lines) {
            String[] separatedLine = line.split(",");

            int id = Integer.parseInt(separatedLine[0]);
            int year = Integer.parseInt(separatedLine[1]);
            String title = separatedLine[2];
            if(separatedLine.length > 3)
            {
                for(int i = 3; i < separatedLine.length; i++)
                {
                    title += "," + separatedLine[i];
                }
            }
            Movie movie = new Movie(id, year, title);
            movies.add(movie);
        }
        return movies;

        /*
        List<Movie> getAllMovies = new ArrayList<>();

        try {
            //Læser alle linjer fra movie_title.txt
            List<String> lines = Files.readAllLines(Paths.get(MOVIES_FILE));

            //Foreach loop som tjekker alle linjer igennem og laver nye Movie Objects
            for (String line : lines) {

                String[] parts = line.split(",");
                String title = parts[2].trim();
                int year = Integer.parseInt(parts[1].trim());
                int id = Integer.parseInt(parts[0].trim());

                Movie movie = new Movie(id, year, title);
                getAllMovies.add(movie);

            }
            return getAllMovies;
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
        */

    }

    @Override
    public Movie createMovie(Movie movie) throws Exception {

        List<String> movies = Files.readAllLines(Path.of(MOVIES_FILE));

        if (movies.size() > 0) {
            // get next id
            String[] separatedLine = movies.get(movies.size() - 1).split(",");
            int nextId = Integer.parseInt(separatedLine[0]) + 1;
            String newMovieLine = nextId + "," + movie.getYear() + "," + movie.getTitle();
            Files.write(Path.of(MOVIES_FILE), (newMovieLine + "\r\n").getBytes(), APPEND);

            return new Movie(nextId, movie.getYear(), movie.getTitle());
        }
        return null;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        List<String> movies = Files.readAllLines(Path.of(MOVIES_FILE));

        try {
            int movieId = movie.getId();
            if (movieId == movie.getId())

            movies.remove(movie);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }






    /*
    public List<Movie> getAllMovies() {
        List<Movie> allMovieList = new ArrayList<>();

        File moviesFile = new File(MOVIES_FILE);


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(moviesFile))) {
            boolean hasLines = true;
            while (hasLines) {
                String line = bufferedReader.readLine();
                hasLines = (line != null);
                if (hasLines && !line.isBlank())
                {
                    String[] separatedLine = line.split(",");

                    int id = Integer.parseInt(separatedLine[0]);
                    int year = Integer.parseInt(separatedLine[1]);
                    String title = separatedLine[2];
                    if(separatedLine.length > 3)
                    {
                        for(int i = 3; i < separatedLine.length; i++)
                        {
                            title += "," + separatedLine[i];
                        }
                    }
                    Movie movie = new Movie(id, title, year);
                    allMovieList.add(movie);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allMovieList;
    }
    */


}