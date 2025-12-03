package com.amazonaws.samples.qdevmovies.movies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Arrr! Test class for the MovieService treasure chest operations, me hearty!
 * These tests ensure our movie search functionality works ship-shape.
 */
public class MovieServiceTest {

    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        // Create a fresh MovieService instance for each test, ye savvy pirate!
        movieService = new MovieService();
    }

    @Test
    @DisplayName("Arrr! Test getting all movie treasures from the chest")
    public void testGetAllMovies() {
        List<Movie> allMovies = movieService.getAllMovies();
        
        assertNotNull(allMovies, "Movie treasure chest should not be null, ye scallywag!");
        assertFalse(allMovies.isEmpty(), "Movie treasure chest should contain some treasures!");
        
        // Verify we have the expected number of movies from the JSON file
        assertEquals(12, allMovies.size(), "Should have 12 movie treasures in the chest!");
    }

    @Test
    @DisplayName("Arrr! Test finding a specific movie treasure by ID")
    public void testGetMovieById() {
        // Test finding an existing movie treasure
        Optional<Movie> movie = movieService.getMovieById(1L);
        assertTrue(movie.isPresent(), "Should find the movie treasure with ID 1!");
        assertEquals("The Prison Escape", movie.get().getMovieName());
        assertEquals("John Director", movie.get().getDirector());

        // Test finding another existing movie treasure
        Optional<Movie> movie2 = movieService.getMovieById(5L);
        assertTrue(movie2.isPresent(), "Should find the movie treasure with ID 5!");
        assertEquals("Life Journey", movie2.get().getMovieName());
    }

    @Test
    @DisplayName("Arrr! Test searching for non-existent movie treasure by ID")
    public void testGetMovieByIdNotFound() {
        Optional<Movie> movie = movieService.getMovieById(999L);
        assertFalse(movie.isPresent(), "Should not find a movie treasure with non-existent ID!");
    }

    @Test
    @DisplayName("Arrr! Test invalid movie ID parameters")
    public void testGetMovieByIdInvalid() {
        // Test null ID
        Optional<Movie> movie1 = movieService.getMovieById(null);
        assertFalse(movie1.isPresent(), "Should not find movie with null ID, ye landlubber!");

        // Test negative ID
        Optional<Movie> movie2 = movieService.getMovieById(-1L);
        assertFalse(movie2.isPresent(), "Should not find movie with negative ID!");

        // Test zero ID
        Optional<Movie> movie3 = movieService.getMovieById(0L);
        assertFalse(movie3.isPresent(), "Should not find movie with zero ID!");
    }

    @Test
    @DisplayName("Arrr! Test searching movie treasures by name")
    public void testSearchMoviesByName() {
        // Test exact name match (case-insensitive)
        List<Movie> results1 = movieService.searchMoviesByName("The Prison Escape");
        assertEquals(1, results1.size(), "Should find exactly one movie treasure!");
        assertEquals("The Prison Escape", results1.get(0).getMovieName());

        // Test partial name match (case-insensitive)
        List<Movie> results2 = movieService.searchMoviesByName("the");
        assertTrue(results2.size() >= 4, "Should find multiple movies with 'the' in the name!");

        // Test case-insensitive search
        List<Movie> results3 = movieService.searchMoviesByName("PRISON");
        assertEquals(1, results3.size(), "Case-insensitive search should work, me hearty!");
        assertEquals("The Prison Escape", results3.get(0).getMovieName());
    }

    @Test
    @DisplayName("Arrr! Test searching movie treasures by genre")
    public void testSearchMoviesByGenre() {
        // Test exact genre match
        List<Movie> dramaMovies = movieService.searchMoviesByGenre("Drama");
        assertTrue(dramaMovies.size() >= 2, "Should find multiple drama movie treasures!");

        // Test partial genre match
        List<Movie> crimeMovies = movieService.searchMoviesByGenre("Crime");
        assertTrue(crimeMovies.size() >= 3, "Should find multiple crime movie treasures!");

        // Test case-insensitive genre search
        List<Movie> actionMovies = movieService.searchMoviesByGenre("action");
        assertTrue(actionMovies.size() >= 2, "Should find action movies with case-insensitive search!");
    }

    @Test
    @DisplayName("Arrr! Test comprehensive movie treasure search with multiple parameters")
    public void testSearchMoviesWithMultipleParameters() {
        // Test search by name and genre
        List<Movie> results1 = movieService.searchMovies("Family", null, "Crime");
        assertEquals(1, results1.size(), "Should find 'The Family Boss' movie!");
        assertEquals("The Family Boss", results1.get(0).getMovieName());

        // Test search by ID only (should ignore other parameters)
        List<Movie> results2 = movieService.searchMovies("SomeRandomName", 3L, "SomeRandomGenre");
        assertEquals(1, results2.size(), "ID search should ignore other parameters!");
        assertEquals("The Masked Hero", results2.get(0).getMovieName());

        // Test search with no matching results
        List<Movie> results3 = movieService.searchMovies("NonExistentMovie", null, null);
        assertTrue(results3.isEmpty(), "Should return empty list for non-existent movie!");
    }

    @Test
    @DisplayName("Arrr! Test search with empty and null parameters")
    public void testSearchMoviesWithEmptyParameters() {
        // Test with all null parameters
        List<Movie> results1 = movieService.searchMovies(null, null, null);
        assertEquals(12, results1.size(), "Should return all movies when no filters applied!");

        // Test with empty strings
        List<Movie> results2 = movieService.searchMovies("", null, "");
        assertEquals(12, results2.size(), "Should return all movies with empty string parameters!");

        // Test with whitespace-only strings
        List<Movie> results3 = movieService.searchMovies("   ", null, "   ");
        assertEquals(12, results3.size(), "Should return all movies with whitespace-only parameters!");
    }

    @Test
    @DisplayName("Arrr! Test getting all unique genres from the treasure chest")
    public void testGetAllGenres() {
        List<String> genres = movieService.getAllGenres();
        
        assertNotNull(genres, "Genres list should not be null, ye scurvy dog!");
        assertFalse(genres.isEmpty(), "Should have some genres in the treasure chest!");
        
        // Verify some expected genres are present
        assertTrue(genres.contains("Drama"), "Should contain Drama genre!");
        assertTrue(genres.contains("Action/Crime"), "Should contain Action/Crime genre!");
        assertTrue(genres.contains("Adventure/Fantasy"), "Should contain Adventure/Fantasy genre!");
        
        // Verify genres are unique (no duplicates)
        long uniqueCount = genres.stream().distinct().count();
        assertEquals(genres.size(), uniqueCount, "All genres should be unique, no duplicates allowed!");
    }

    @Test
    @DisplayName("Arrr! Test edge cases for movie search functionality")
    public void testSearchMoviesEdgeCases() {
        // Test search with very long string
        String longString = "a".repeat(1000);
        List<Movie> results1 = movieService.searchMovies(longString, null, null);
        assertTrue(results1.isEmpty(), "Should handle very long search strings gracefully!");

        // Test search with special characters
        List<Movie> results2 = movieService.searchMovies("@#$%^&*()", null, null);
        assertTrue(results2.isEmpty(), "Should handle special characters in search!");

        // Test search with numbers in movie name
        List<Movie> results3 = movieService.searchMovies("2008", null, null);
        assertEquals(1, results3.size(), "Should find movies with years in description!");
        assertEquals("The Masked Hero", results3.get(0).getMovieName());
    }

    @Test
    @DisplayName("Arrr! Test search performance with multiple concurrent searches")
    public void testSearchMoviesPerformance() {
        // This test ensures our search doesn't have performance issues, me hearty!
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 100; i++) {
            movieService.searchMovies("The", null, "Drama");
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        // Should complete 100 searches in reasonable time (less than 1 second)
        assertTrue(duration < 1000, "Search operations should be fast, ye savvy pirate!");
    }
}