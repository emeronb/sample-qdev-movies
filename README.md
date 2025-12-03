# 🏴‍☠️ Pirate's Movie Treasure Chest - Spring Boot Demo Application

Ahoy, me hearty! Welcome to the Pirate's Movie Treasure Chest - a swashbuckling movie catalog web application built with Spring Boot, demonstrating Java application development best practices with a pirate twist!

## ⚓ Features

- **Movie Treasure Chest**: Browse 12 classic movie treasures with detailed information
- **Advanced Search**: Search for movie treasures by name, ID, or genre with our powerful search API
- **Movie Details**: View comprehensive information including director, year, genre, duration, and description
- **Customer Reviews**: Each movie includes authentic customer reviews with ratings and avatars
- **Pirate-Themed Interface**: Enjoy a swashbuckling user experience with pirate language and themes
- **Responsive Design**: Mobile-first design that works on all devices, from ship to shore
- **Modern UI**: Dark theme with gradient backgrounds and smooth animations
- **Error Handling**: Comprehensive error handling with pirate-themed messages

## 🔍 New Search Functionality

Our treasure chest now includes powerful search capabilities, arrr!

### Search Features
- **Search by Name**: Find movies by title (case-insensitive, partial matches)
- **Search by ID**: Find specific movies by their unique ID
- **Search by Genre**: Filter movies by genre categories
- **Combined Search**: Use multiple criteria for refined treasure hunting
- **Interactive Form**: User-friendly search form with dropdown genre selection
- **Real-time Results**: Instant search results with result count display

### Search API Endpoints
- `GET /movies/search?name={movieName}` - Search by movie name
- `GET /movies/search?id={movieId}` - Search by movie ID  
- `GET /movies/search?genre={genre}` - Search by genre
- `GET /movies/search?name={name}&genre={genre}` - Combined search

For detailed API documentation, see [MOVIE_SEARCH_API.md](MOVIE_SEARCH_API.md)

## 🛠️ Technology Stack

- **Java 8**
- **Spring Boot 2.0.5**
- **Maven** for dependency management
- **Thymeleaf** for server-side templating
- **Log4j 2.20.0** for logging
- **JUnit 5.8.2** for testing
- **HTML5/CSS3** with responsive design
- **JavaScript** for interactive features

## 🚀 Quick Start

### Prerequisites

- Java 8 or higher
- Maven 3.6+

### Run the Application

```bash
git clone https://github.com/<youruser>/sample-qdev-movies.git
cd sample-qdev-movies
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Access the Application

- **Movie Treasure Chest**: http://localhost:8080/movies
- **Movie Details**: http://localhost:8080/movies/{id}/details (where {id} is 1-12)
- **Search Movies**: http://localhost:8080/movies/search?name={searchTerm}

## 🏗️ Building for Production

```bash
mvn clean package
java -jar target/sample-qdev-movies-0.1.0.jar
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/amazonaws/samples/qdevmovies/
│   │       ├── movies/
│   │       │   ├── MoviesApplication.java    # Main Spring Boot application
│   │       │   ├── MoviesController.java     # REST controller with search endpoints
│   │       │   ├── MovieService.java         # Business logic and search functionality
│   │       │   ├── Movie.java                # Movie data model
│   │       │   ├── Review.java               # Review data model
│   │       │   └── ReviewService.java        # Review service
│   │       └── utils/
│   │           ├── MovieIconUtils.java       # Movie icon utilities
│   │           └── MovieUtils.java           # Movie validation utilities
│   └── resources/
│       ├── templates/
│       │   ├── movies.html                   # Main movies page with search form
│       │   ├── movie-details.html            # Movie details page
│       │   └── error.html                    # Error page with pirate themes
│       ├── static/css/
│       │   ├── movies.css                    # Styling for movies and search
│       │   └── movie-details.css             # Styling for movie details
│       ├── application.yml                   # Application configuration
│       ├── movies.json                       # Movie data
│       ├── mock-reviews.json                 # Mock review data
│       └── log4j2.xml                        # Logging configuration
└── test/                                     # Comprehensive unit tests
    └── java/
        └── com/amazonaws/samples/qdevmovies/movies/
            ├── MovieServiceTest.java         # Tests for search functionality
            ├── MoviesControllerTest.java     # Tests for controller endpoints
            └── MovieTest.java                # Tests for movie model
```

## 🗺️ API Endpoints

### Get All Movies
```
GET /movies
```
Returns an HTML page displaying all movie treasures with search form and basic information.

### Search Movies (NEW! 🆕)
```
GET /movies/search
```
Search for movie treasures using query parameters.

**Query Parameters:**
- `name` (optional): Movie name to search for (case-insensitive)
- `id` (optional): Specific movie ID (must be positive integer)
- `genre` (optional): Genre to filter by (case-insensitive)

**Examples:**
```
http://localhost:8080/movies/search?name=Prison
http://localhost:8080/movies/search?id=5
http://localhost:8080/movies/search?genre=Drama
http://localhost:8080/movies/search?name=The&genre=Action
```

### Get Movie Details
```
GET /movies/{id}/details
```
Returns an HTML page with detailed movie information and customer reviews.

**Parameters:**
- `id` (path parameter): Movie ID (1-12)

**Example:**
```
http://localhost:8080/movies/1/details
```

## 🎭 Available Movie Genres

Our treasure chest contains movies in these genres:
- Action/Crime
- Action/Sci-Fi  
- Adventure/Fantasy
- Adventure/Sci-Fi
- Crime/Drama
- Drama
- Drama/History
- Drama/Romance
- Drama/Thriller

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Test Coverage
- **MovieService**: Search functionality, data operations
- **MoviesController**: All endpoints including search
- **Movie Model**: Data validation and operations
- **Edge Cases**: Error handling, invalid inputs, empty results

### Manual Testing
1. Navigate to http://localhost:8080/movies
2. Use the search form to test different criteria
3. Verify search results and error handling
4. Test responsive design on different screen sizes

## 🚨 Troubleshooting

### Port 8080 already in use
Run on a different port:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

### Build failures
Clean and rebuild:
```bash
mvn clean compile
```

### Search not working
- Check that at least one search parameter is provided
- Verify movie ID is a positive integer
- Check application logs for detailed error messages

### No search results
- Try partial matches instead of exact matches
- Check spelling of search terms
- Verify the movie exists in the dataset (movies.json)

## 🤝 Contributing

This project demonstrates modern Spring Boot development with search functionality. Feel free to:
- Add more movies to the treasure chest (movies.json)
- Enhance the pirate-themed UI/UX
- Add new search features (year range, rating filters, etc.)
- Improve the responsive design
- Add more comprehensive error handling
- Extend the API with JSON responses

### Development Guidelines
- Follow pirate-themed naming conventions in comments and messages
- Add comprehensive unit tests for new features
- Update documentation for any API changes
- Maintain the existing code style and structure

## 📚 Documentation

- [Movie Search API Documentation](MOVIE_SEARCH_API.md) - Detailed API reference
- [Code of Conduct](CODE_OF_CONDUCT.md) - Community guidelines
- [License](LICENSE) - MIT-0 License details

## 🏴‍☠️ Pirate Features

This application includes fun pirate-themed elements:
- Pirate language in error messages and logs
- Treasure chest metaphors for movie collections
- Swashbuckling UI elements and icons
- Pirate-themed variable names and comments
- Nautical terminology throughout the codebase

## 📈 Performance

- In-memory search operations for fast response times
- Efficient filtering algorithms with stream processing
- Cached genre lists for dropdown population
- Optimized CSS and JavaScript for smooth user experience

## 🔒 Security

- Input validation for all search parameters
- XSS protection through Thymeleaf templating
- Safe parameter handling and encoding
- Comprehensive error handling without information leakage

## 📄 License

This sample code is licensed under the MIT-0 License. See the LICENSE file.

---

Arrr! Happy treasure hunting, me hearty! May ye find the movie treasures ye seek! 🏴‍☠️⚓🎬
