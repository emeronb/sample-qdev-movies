# 🏴‍☠️ Pirate's Movie Search API Documentation

Ahoy, me hearty! Welcome to the Movie Search API documentation for our treasure chest of movies! This API allows ye to search through our vast collection of movie treasures using various criteria.

## 🎬 Overview

The Movie Search API provides powerful search capabilities for finding movie treasures in our collection. Whether ye be looking for a specific movie by name, ID, or genre, this API will help ye navigate through our treasure chest like a true pirate!

## ⚓ Base URL

```
http://localhost:8080
```

## 🗺️ API Endpoints

### 1. Get All Movies
**Endpoint:** `GET /movies`

**Description:** Retrieves all movie treasures from our collection, arrr!

**Response:** Returns the movies HTML page with all available movies and a search form.

**Example:**
```bash
curl -X GET http://localhost:8080/movies
```

### 2. Search Movies
**Endpoint:** `GET /movies/search`

**Description:** Search for movie treasures using various criteria, ye savvy pirate!

**Query Parameters:**
- `name` (optional): Movie name to search for (case-insensitive, partial matches supported)
- `id` (optional): Specific movie ID to find (must be a positive integer)
- `genre` (optional): Genre to filter by (case-insensitive, partial matches supported)

**Validation Rules:**
- At least one parameter must be provided, ye scallywag!
- Movie ID must be a positive integer (> 0)
- Empty strings and whitespace-only parameters are treated as not provided

**Response:** Returns the movies HTML page with search results or an error page if no matches found.

## 🔍 Search Examples

### Search by Movie Name
Find all movies containing "Prison" in the name:
```bash
curl -X GET "http://localhost:8080/movies/search?name=Prison"
```

### Search by Movie ID
Find a specific movie by its ID:
```bash
curl -X GET "http://localhost:8080/movies/search?id=5"
```

### Search by Genre
Find all movies in the "Drama" genre:
```bash
curl -X GET "http://localhost:8080/movies/search?genre=Drama"
```

### Combined Search
Search for movies with "The" in the name and "Action" genre:
```bash
curl -X GET "http://localhost:8080/movies/search?name=The&genre=Action"
```

### Case-Insensitive Search
Search is case-insensitive, so these are equivalent:
```bash
curl -X GET "http://localhost:8080/movies/search?name=PRISON"
curl -X GET "http://localhost:8080/movies/search?name=prison"
curl -X GET "http://localhost:8080/movies/search?name=Prison"
```

## 🎭 Available Genres

Our treasure chest contains movies in the following genres:
- Action/Crime
- Action/Sci-Fi
- Adventure/Fantasy
- Adventure/Sci-Fi
- Crime/Drama
- Drama
- Drama/History
- Drama/Romance
- Drama/Thriller

## 📊 Response Format

### Successful Search
When movies are found, the response includes:
- HTML page with search results
- Search result count
- Search parameters used
- Movie cards with details

### Error Responses
When no movies are found or invalid parameters are provided:
- Error page with pirate-themed error message
- Explanation of the issue
- Suggestions for fixing the problem
- Navigation options to return to main page

## 🚨 Error Handling

### No Search Parameters
**Error:** "Arrr! Ye need to provide at least one search parameter, me hearty!"
**Solution:** Provide at least one of: name, id, or genre

### Invalid Movie ID
**Error:** "Arrr! That movie ID be invalid, ye scallywag!"
**Solution:** Use a positive integer for the movie ID

### No Results Found
**Error:** "Arrr! No movie treasures match yer search criteria, me hearty!"
**Solution:** Try different search terms or check spelling

### System Error
**Error:** "Arrr! Something went wrong during the treasure hunt, me hearty!"
**Solution:** Try again later or contact support

## 🎨 HTML Form Interface

The main movies page (`/movies`) includes a search form with:

### Form Fields
1. **Movie Name Input**
   - Text field for entering movie name
   - Placeholder: "Enter movie name, ye savvy pirate..."
   - Supports partial matching

2. **Movie ID Input**
   - Number field for specific movie ID
   - Minimum value: 1
   - Placeholder: "Enter specific movie ID..."

3. **Genre Dropdown**
   - Select field with all available genres
   - Default option: "Select a genre, me hearty..."
   - Populated dynamically from movie data

### Form Actions
- **Search Treasures!** button: Submits the search
- **Clear Search** button: Clears all fields and returns to all movies

## 🧪 Testing the API

### Manual Testing
1. Open your browser and navigate to `http://localhost:8080/movies`
2. Use the search form to test different search criteria
3. Verify that results are displayed correctly
4. Test error cases by providing invalid input

### Automated Testing
Run the unit tests to verify functionality:
```bash
./gradlew test
# or
mvn test
```

## 🏗️ Implementation Details

### Backend Components
- **MovieService**: Handles movie data operations and search logic
- **MoviesController**: REST endpoints and request handling
- **Movie**: Data model representing movie entities

### Frontend Components
- **movies.html**: Main template with search form and results display
- **error.html**: Error page template for handling search errors
- **CSS**: Pirate-themed styling for search interface

### Search Algorithm
1. **ID Search**: If movie ID is provided, return specific movie (ignores other parameters)
2. **Name Filter**: Filter movies by name containing the search term (case-insensitive)
3. **Genre Filter**: Filter movies by genre containing the search term (case-insensitive)
4. **Combined Filters**: Apply name and genre filters together for refined results

## 🔧 Configuration

### Application Properties
The search functionality uses the existing application configuration. No additional setup required!

### Movie Data
Movies are loaded from `src/main/resources/movies.json`. The search operates on this static dataset.

## 🚀 Performance Considerations

- Search operations are performed in-memory for fast response times
- Case-insensitive matching uses lowercase conversion
- Partial string matching uses `contains()` for flexibility
- Genre list is cached for dropdown population

## 🐛 Troubleshooting

### Common Issues

1. **Search returns no results**
   - Check spelling of search terms
   - Try partial matches instead of exact matches
   - Verify the movie exists in the dataset

2. **Form doesn't submit**
   - Ensure JavaScript is enabled
   - Check browser console for errors
   - Verify form fields are properly filled

3. **Invalid ID error**
   - Use only positive integers for movie ID
   - Don't include letters or special characters

### Debug Tips
- Check application logs for detailed error messages
- Use browser developer tools to inspect network requests
- Verify URL parameters are properly encoded

## 📝 API Changelog

### Version 1.0.0 (Current)
- Initial implementation of movie search functionality
- Added search by name, ID, and genre
- Implemented HTML form interface
- Added comprehensive error handling
- Created pirate-themed user experience

## 🤝 Contributing

To contribute to the Movie Search API:
1. Follow the existing pirate-themed naming conventions
2. Add comprehensive unit tests for new features
3. Update this documentation for any API changes
4. Ensure error messages maintain the pirate theme

## 📞 Support

If ye be having trouble with the Movie Search API, check the application logs or contact the development crew. Remember, every good pirate knows how to read the treasure map (documentation)!

Arrr! Happy treasure hunting, me hearty! 🏴‍☠️