package com.amazonaws.samples.qdevmovies.movies;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private static final Logger logger = LogManager.getLogger(MovieService.class);
    private final List<Movie> movies;
    private final Map<Long, Movie> movieMap;

    public MovieService() {
        this.movies = loadMoviesFromJson();
        this.movieMap = new HashMap<>();
        for (Movie movie : movies) {
            movieMap.put(movie.getId(), movie);
        }
    }

    private List<Movie> loadMoviesFromJson() {
        List<Movie> movieList = new ArrayList<>();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movies.json");
            if (inputStream != null) {
                Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
                String jsonContent = scanner.useDelimiter("\\A").next();
                scanner.close();
                
                JSONArray moviesArray = new JSONArray(jsonContent);
                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movieObj = moviesArray.getJSONObject(i);
                    movieList.add(new Movie(
                        movieObj.getLong("id"),
                        movieObj.getString("movieName"),
                        movieObj.getString("director"),
                        movieObj.getInt("year"),
                        movieObj.getString("genre"),
                        movieObj.getString("description"),
                        movieObj.getInt("duration"),
                        movieObj.getDouble("imdbRating")
                    ));
                }
            }
        } catch (Exception e) {
            logger.error("Failed to load movies from JSON: {}", e.getMessage());
        }
        return movieList;
    }

    public List<Movie> getAllMovies() {
        return movies;
    }

    public Optional<Movie> getMovieById(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(movieMap.get(id));
    }

    /**
     * Ahoy matey! Search for treasure (movies) in our vast collection!
     * This method be the main search crew member that filters movies by various criteria.
     * 
     * @param movieName The name of the movie treasure ye be seekin' (case-insensitive)
     * @param movieId The specific ID of the movie treasure
     * @param genre The genre of movies ye want to discover
     * @return A list of movie treasures that match yer search criteria, arrr!
     */
    public List<Movie> searchMovies(String movieName, Long movieId, String genre) {
        logger.info("Ahoy! Starting treasure hunt for movies with name: '{}', id: {}, genre: '{}'", 
                   movieName, movieId, genre);
        
        List<Movie> treasureChest = new ArrayList<>(movies);
        
        // Filter by movie ID first, as it be the most specific search, arrr!
        if (movieId != null && movieId > 0) {
            logger.debug("Searching for specific movie treasure with ID: {}", movieId);
            Optional<Movie> specificTreasure = getMovieById(movieId);
            return specificTreasure.map(List::of).orElse(new ArrayList<>());
        }
        
        // Filter by movie name if provided, ye savvy pirate!
        if (movieName != null && !movieName.trim().isEmpty()) {
            String searchName = movieName.trim().toLowerCase();
            logger.debug("Filtering treasure chest by movie name containing: '{}'", searchName);
            treasureChest = treasureChest.stream()
                .filter(movie -> movie.getMovieName().toLowerCase().contains(searchName))
                .collect(Collectors.toList());
        }
        
        // Filter by genre if provided, me hearty!
        if (genre != null && !genre.trim().isEmpty()) {
            String searchGenre = genre.trim().toLowerCase();
            logger.debug("Filtering treasure chest by genre containing: '{}'", searchGenre);
            treasureChest = treasureChest.stream()
                .filter(movie -> movie.getGenre().toLowerCase().contains(searchGenre))
                .collect(Collectors.toList());
        }
        
        logger.info("Arrr! Found {} movie treasures matching yer search criteria!", treasureChest.size());
        return treasureChest;
    }

    /**
     * Search for movies by name only, ye landlubber!
     * 
     * @param movieName The name of the movie to search for (case-insensitive)
     * @return List of movies whose names contain the search term
     */
    public List<Movie> searchMoviesByName(String movieName) {
        return searchMovies(movieName, null, null);
    }

    /**
     * Search for movies by genre only, me buccaneer!
     * 
     * @param genre The genre to search for (case-insensitive)
     * @return List of movies whose genre contains the search term
     */
    public List<Movie> searchMoviesByGenre(String genre) {
        return searchMovies(null, null, genre);
    }

    /**
     * Get all unique genres from our treasure chest of movies, arrr!
     * 
     * @return List of all unique genres available in the movie collection
     */
    public List<String> getAllGenres() {
        logger.debug("Gathering all genres from our movie treasure chest");
        return movies.stream()
            .map(Movie::getGenre)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
}
