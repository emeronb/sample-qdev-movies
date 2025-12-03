package com.amazonaws.samples.qdevmovies.movies;

import com.amazonaws.samples.qdevmovies.utils.MovieIconUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

@Controller
public class MoviesController {
    private static final Logger logger = LogManager.getLogger(MoviesController.class);

    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/movies")
    public String getMovies(org.springframework.ui.Model model) {
        logger.info("Ahoy! Fetching all movie treasures from our collection");
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("allGenres", movieService.getAllGenres());
        return "movies";
    }

    /**
     * Arrr! The main search endpoint for finding movie treasures, me hearty!
     * Accepts query parameters: name, id, and genre to filter the treasure chest.
     * 
     * @param movieName Optional movie name to search for (case-insensitive)
     * @param movieId Optional specific movie ID to find
     * @param genre Optional genre to filter by (case-insensitive)
     * @param model Spring model for passing data to the view
     * @return The movies template with search results, or error page if something goes wrong
     */
    @GetMapping("/movies/search")
    public String searchMovies(
            @RequestParam(value = "name", required = false) String movieName,
            @RequestParam(value = "id", required = false) Long movieId,
            @RequestParam(value = "genre", required = false) String genre,
            org.springframework.ui.Model model) {
        
        logger.info("Ahoy! Starting treasure hunt with parameters - name: '{}', id: {}, genre: '{}'", 
                   movieName, movieId, genre);
        
        try {
            // Validate that at least one search parameter is provided, ye scurvy dog!
            if ((movieName == null || movieName.trim().isEmpty()) && 
                movieId == null && 
                (genre == null || genre.trim().isEmpty())) {
                
                logger.warn("Arrr! No search parameters provided by the landlubber!");
                model.addAttribute("title", "Search Error - Ahoy!");
                model.addAttribute("message", "Arrr! Ye need to provide at least one search parameter, me hearty! " +
                                           "Try searching by movie name, ID, or genre to find yer treasure!");
                model.addAttribute("searchError", true);
                return "error";
            }
            
            // Validate movie ID if provided (must be positive, ye savvy?)
            if (movieId != null && movieId <= 0) {
                logger.warn("Arrr! Invalid movie ID provided: {}", movieId);
                model.addAttribute("title", "Invalid Movie ID - Shiver Me Timbers!");
                model.addAttribute("message", "Arrr! That movie ID be invalid, ye scallywag! " +
                                           "Movie IDs must be positive numbers greater than 0!");
                model.addAttribute("searchError", true);
                return "error";
            }
            
            // Search for movie treasures using our trusty service crew member
            List<Movie> searchResults = movieService.searchMovies(movieName, movieId, genre);
            
            // Handle empty results with a pirate message, arrr!
            if (searchResults.isEmpty()) {
                logger.info("Arrr! No movie treasures found matching the search criteria");
                model.addAttribute("title", "No Treasures Found - Batten Down the Hatches!");
                model.addAttribute("message", "Arrr! No movie treasures match yer search criteria, me hearty! " +
                                           "Try different search terms or check yer spelling, ye landlubber!");
                model.addAttribute("searchError", true);
                model.addAttribute("searchParams", buildSearchParamsString(movieName, movieId, genre));
                return "error";
            }
            
            // Success! We found some movie treasures, arrr!
            logger.info("Arrr! Successfully found {} movie treasures!", searchResults.size());
            model.addAttribute("movies", searchResults);
            model.addAttribute("allGenres", movieService.getAllGenres());
            model.addAttribute("searchResults", true);
            model.addAttribute("searchCount", searchResults.size());
            model.addAttribute("searchParams", buildSearchParamsString(movieName, movieId, genre));
            
            return "movies";
            
        } catch (NumberFormatException e) {
            logger.error("Arrr! Invalid number format in search parameters: {}", e.getMessage());
            model.addAttribute("title", "Invalid Search Parameters - Scurvy Bug!");
            model.addAttribute("message", "Arrr! There be a problem with yer search parameters, ye scallywag! " +
                                       "Make sure the movie ID be a proper number!");
            model.addAttribute("searchError", true);
            return "error";
        } catch (Exception e) {
            logger.error("Arrr! Unexpected error during movie search: {}", e.getMessage(), e);
            model.addAttribute("title", "Search Error - Kraken Attack!");
            model.addAttribute("message", "Arrr! Something went wrong during the treasure hunt, me hearty! " +
                                       "The kraken might have attacked our search system. Try again later!");
            model.addAttribute("searchError", true);
            return "error";
        }
    }

    /**
     * Helper method to build a readable string of search parameters for display, arrr!
     */
    private String buildSearchParamsString(String movieName, Long movieId, String genre) {
        StringBuilder params = new StringBuilder();
        
        if (movieName != null && !movieName.trim().isEmpty()) {
            params.append("Name: '").append(movieName.trim()).append("'");
        }
        
        if (movieId != null) {
            if (params.length() > 0) params.append(", ");
            params.append("ID: ").append(movieId);
        }
        
        if (genre != null && !genre.trim().isEmpty()) {
            if (params.length() > 0) params.append(", ");
            params.append("Genre: '").append(genre.trim()).append("'");
        }
        
        return params.toString();
    }

    @GetMapping("/movies/{id}/details")
    public String getMovieDetails(@PathVariable("id") Long movieId, org.springframework.ui.Model model) {
        logger.info("Fetching details for movie ID: {}", movieId);
        
        Optional<Movie> movieOpt = movieService.getMovieById(movieId);
        if (!movieOpt.isPresent()) {
            logger.warn("Movie with ID {} not found", movieId);
            model.addAttribute("title", "Movie Not Found");
            model.addAttribute("message", "Movie with ID " + movieId + " was not found.");
            return "error";
        }
        
        Movie movie = movieOpt.get();
        model.addAttribute("movie", movie);
        model.addAttribute("movieIcon", MovieIconUtils.getMovieIcon(movie.getMovieName()));
        model.addAttribute("allReviews", reviewService.getReviewsForMovie(movie.getId()));
        
        return "movie-details";
    }
}