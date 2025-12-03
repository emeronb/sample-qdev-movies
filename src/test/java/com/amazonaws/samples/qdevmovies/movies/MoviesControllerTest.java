package com.amazonaws.samples.qdevmovies.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Arrr! Test class for the MoviesController treasure map navigation, me hearty!
 * These tests ensure our movie controller handles all requests ship-shape.
 */
public class MoviesControllerTest {

    private MoviesController moviesController;
    private Model model;
    private MockMovieService mockMovieService;
    private ReviewService mockReviewService;

    /**
     * Mock MovieService for testing purposes, ye savvy pirate!
     */
    private static class MockMovieService extends MovieService {
        private final List<Movie> testMovies;
        private final List<String> testGenres;

        public MockMovieService() {
            // Create test movie treasures for our mock service
            this.testMovies = Arrays.asList(
                new Movie(1L, "Test Pirate Movie", "Captain Director", 2023, "Adventure", "A swashbuckling adventure", 120, 4.5),
                new Movie(2L, "The Treasure Hunt", "Buccaneer Filmmaker", 2022, "Action/Adventure", "Searching for buried treasure", 110, 4.0),
                new Movie(3L, "Drama on the High Seas", "Seafaring Director", 2021, "Drama", "Emotional pirate story", 130, 3.5)
            );
            this.testGenres = Arrays.asList("Adventure", "Action/Adventure", "Drama");
        }

        @Override
        public List<Movie> getAllMovies() {
            return new ArrayList<>(testMovies);
        }

        @Override
        public Optional<Movie> getMovieById(Long id) {
            return testMovies.stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst();
        }

        @Override
        public List<Movie> searchMovies(String movieName, Long movieId, String genre) {
            List<Movie> results = new ArrayList<>(testMovies);

            // Filter by ID first (most specific)
            if (movieId != null && movieId > 0) {
                return getMovieById(movieId).map(Arrays::asList).orElse(new ArrayList<>());
            }

            // Filter by name
            if (movieName != null && !movieName.trim().isEmpty()) {
                String searchName = movieName.trim().toLowerCase();
                results = results.stream()
                    .filter(movie -> movie.getMovieName().toLowerCase().contains(searchName))
                    .collect(java.util.stream.Collectors.toList());
            }

            // Filter by genre
            if (genre != null && !genre.trim().isEmpty()) {
                String searchGenre = genre.trim().toLowerCase();
                results = results.stream()
                    .filter(movie -> movie.getGenre().toLowerCase().contains(searchGenre))
                    .collect(java.util.stream.Collectors.toList());
            }

            return results;
        }

        @Override
        public List<String> getAllGenres() {
            return new ArrayList<>(testGenres);
        }
    }

    @BeforeEach
    public void setUp() {
        moviesController = new MoviesController();
        model = new ExtendedModelMap();
        
        // Create mock services
        mockMovieService = new MockMovieService();
        
        mockReviewService = new ReviewService() {
            @Override
            public List<Review> getReviewsForMovie(long movieId) {
                return new ArrayList<>();
            }
        };
        
        // Inject mocks using reflection, arrr!
        try {
            java.lang.reflect.Field movieServiceField = MoviesController.class.getDeclaredField("movieService");
            movieServiceField.setAccessible(true);
            movieServiceField.set(moviesController, mockMovieService);
            
            java.lang.reflect.Field reviewServiceField = MoviesController.class.getDeclaredField("reviewService");
            reviewServiceField.setAccessible(true);
            reviewServiceField.set(moviesController, mockReviewService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock services, ye scallywag!", e);
        }
    }

    @Test
    @DisplayName("Arrr! Test getting all movie treasures")
    public void testGetMovies() {
        String result = moviesController.getMovies(model);
        
        assertNotNull(result, "Result should not be null, me hearty!");
        assertEquals("movies", result, "Should return movies template!");
        
        // Verify model attributes
        assertTrue(model.containsAttribute("movies"), "Model should contain movies attribute!");
        assertTrue(model.containsAttribute("allGenres"), "Model should contain allGenres attribute!");
        
        @SuppressWarnings("unchecked")
        List<Movie> movies = (List<Movie>) model.getAttribute("movies");
        assertEquals(3, movies.size(), "Should have 3 test movies!");
    }

    @Test
    @DisplayName("Arrr! Test getting movie details for existing treasure")
    public void testGetMovieDetails() {
        String result = moviesController.getMovieDetails(1L, model);
        
        assertNotNull(result, "Result should not be null!");
        assertEquals("movie-details", result, "Should return movie-details template!");
        
        assertTrue(model.containsAttribute("movie"), "Model should contain movie attribute!");
        assertTrue(model.containsAttribute("movieIcon"), "Model should contain movieIcon attribute!");
        assertTrue(model.containsAttribute("allReviews"), "Model should contain allReviews attribute!");
    }

    @Test
    @DisplayName("Arrr! Test getting movie details for non-existent treasure")
    public void testGetMovieDetailsNotFound() {
        String result = moviesController.getMovieDetails(999L, model);
        
        assertNotNull(result, "Result should not be null!");
        assertEquals("error", result, "Should return error template for non-existent movie!");
        
        assertTrue(model.containsAttribute("title"), "Model should contain error title!");
        assertTrue(model.containsAttribute("message"), "Model should contain error message!");
    }

    @Test
    @DisplayName("Arrr! Test successful movie search by name")
    public void testSearchMoviesByName() {
        String result = moviesController.searchMovies("Pirate", null, null, model);
        
        assertEquals("movies", result, "Should return movies template!");
        assertTrue(model.containsAttribute("searchResults"), "Should indicate search results!");
        assertTrue(model.containsAttribute("searchCount"), "Should contain search count!");
        
        @SuppressWarnings("unchecked")
        List<Movie> movies = (List<Movie>) model.getAttribute("movies");
        assertEquals(1, movies.size(), "Should find 1 movie with 'Pirate' in name!");
        assertEquals("Test Pirate Movie", movies.get(0).getMovieName());
    }

    @Test
    @DisplayName("Arrr! Test successful movie search by ID")
    public void testSearchMoviesById() {
        String result = moviesController.searchMovies(null, 2L, null, model);
        
        assertEquals("movies", result, "Should return movies template!");
        
        @SuppressWarnings("unchecked")
        List<Movie> movies = (List<Movie>) model.getAttribute("movies");
        assertEquals(1, movies.size(), "Should find exactly 1 movie by ID!");
        assertEquals("The Treasure Hunt", movies.get(0).getMovieName());
    }

    @Test
    @DisplayName("Arrr! Test search with no parameters (error case)")
    public void testSearchMoviesNoParameters() {
        String result = moviesController.searchMovies(null, null, null, model);
        
        assertEquals("error", result, "Should return error template!");
        assertTrue(model.containsAttribute("searchError"), "Should indicate search error!");
        assertTrue(model.containsAttribute("title"), "Should contain error title!");
        assertTrue(model.containsAttribute("message"), "Should contain error message!");
        
        String message = (String) model.getAttribute("message");
        assertTrue(message.contains("Arrr!"), "Error message should be pirate-themed!");
    }

    @Test
    @DisplayName("Arrr! Test search with invalid movie ID")
    public void testSearchMoviesInvalidId() {
        String result = moviesController.searchMovies(null, -1L, null, model);
        
        assertEquals("error", result, "Should return error template for invalid ID!");
        assertTrue(model.containsAttribute("searchError"), "Should indicate search error!");
        
        String title = (String) model.getAttribute("title");
        assertTrue(title.contains("Invalid Movie ID"), "Should indicate invalid ID error!");
    }

    @Test
    @DisplayName("Arrr! Test search with no matching results")
    public void testSearchMoviesNoResults() {
        String result = moviesController.searchMovies("NonExistentMovie", null, null, model);
        
        assertEquals("error", result, "Should return error template for no results!");
        assertTrue(model.containsAttribute("searchError"), "Should indicate search error!");
        assertTrue(model.containsAttribute("searchParams"), "Should contain search parameters!");
        
        String title = (String) model.getAttribute("title");
        assertTrue(title.contains("No Treasures Found"), "Should indicate no results found!");
    }
}
