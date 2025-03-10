
# 📚 Virtual Book Store

A modern digital library system built with Spring Boot that offers comprehensive book management features.

## 🌟 Features

- 📖 Search and view books
- 🛒 Purchase books online
- 📱 Borrow digital copies
- 🔐 Secure authentication system
- 💾 Persistent data storage
- ⚡ Redis caching for improved performance
- 🔑 Role-based access control

## 🏗️ Architecture

The project follows a modular architecture with the following components:

- **App Module**: Contains the main class and also data initialization before loading the application context
- **Api Module**: Defines the endpoints
- **Commons Module**: Contains DTOs and exception class
- **REST Client Module**: Independent module for API testing and logging
- **Core Module**: Contains the main services throughout the app and application level configurations
 - **Security Module**: Configurations for security
 - **Domain Module**: Defines the Business logic of the app
 - **Data Module**: Persistence  layer for recording entities
 - **Web Module**: UI repository (Coming Soon)

## 🔌 API Endpoints

### Authentication Endpoints
```http
POST    /api/auth/signup     # Create new user account
POST    /api/auth/login      # Authenticate user
POST    /api/auth/refresh    # Refresh authentication token
POST    /api/auth/logout     # End user session
```

### Book Management Endpoints
```http
GET     /api/book/all           # Retrieve all books
GET     /api/book/{id}          # Get book by ID
POST    /api/book/save          # Add new book (ADMIN only)
GET     /api/book/search        # Search books
GET     /api/book/countByAuthor # Get books count by author
GET     /api/book/countByAuthors # Get books count by multiple authors
```

### Borrowing Endpoints
```http
POST    /api/borrow/set        # Borrow a book (Authenticated users)
PUT     /api/borrow/update     # Update borrowing status (ADMIN only)
```

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Gradle
- Redis Server

### Installation

1. **Redis Setup**
   ```bash
   # For Ubuntu/Debian
   sudo apt-get install redis-server
   sudo systemctl start redis

   # For MacOS
   brew install redis
   brew services start redis
   ```

2. **Build the Project**
   ```bash
   ./gradlew build
   ```

3. **Run the Application**
   ```bash
   ./gradlew :app:bootRun
   ```

## 🧪 Testing

The project includes a dedicated REST client module for testing API endpoints. You can find it in the [restclient](restclient) repository.


## 🔒 Security

- JWT-based authentication
- Role-based access control (ADMIN, USER)
- Secure endpoint protection
- Token refresh mechanism

## 🛠️ Built With

- Spring Boot
- Spring Security
- Redis
- Gradle
- JPA/Hibernate


## Features to be added in the future

- Payment integration for buying books


## License

This project is licensed under [MIT](LICENSE.md)  License.



## Contribution
For contribution see [CONTRIBUTION](CONTRIBUTION.md)



### Thank you for checking out our project
