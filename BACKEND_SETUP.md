# Backend Setup Instructions

## Sweet Shop Server - Spring Boot Backend

This document provides instructions for setting up and running the Spring Boot backend application.

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Installation

### 1. Navigate to the backend directory
```bash
cd sweet-shop-server
```

### 2. Configure MySQL Database

#### Create Database
```sql
-- Connect to MySQL as root
mysql -u root -p

-- Create database
CREATE DATABASE sweetshop;

-- Create user (optional)
CREATE USER 'sweetshop_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON sweetshop.* TO 'sweetshop_user'@'localhost';
FLUSH PRIVILEGES;

-- Exit MySQL
EXIT;
```

#### Update Database Configuration
Edit `src/main/resources/application.properties`:

```properties
# Application name
spring.application.name=sweet-shop-server

# Server port
server.port=8050

# JWT Secret Key
jwt.secretKey=abcdefghijklmnopqrstuvwxyz147852

# MySQL Connection
spring.datasource.url=jdbc:mysql://localhost:3306/sweetshop?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_mysql_password

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 3. Install Dependencies
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The backend will start on http://localhost:8050

## Project Structure

```
sweet-shop-server/
├── src/
│   ├── main/
│   │   ├── java/com/sweet_shop_server/sweet_shop_server/
│   │   │   ├── advices/          # Global exception handling
│   │   │   ├── configuration/    # Security and CORS config
│   │   │   ├── controller/       # REST controllers
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── entity/           # JPA entities
│   │   │   ├── exceptions/       # Custom exceptions
│   │   │   ├── repository/       # Data repositories
│   │   │   ├── security/         # JWT and security
│   │   │   ├── service/          # Business logic
│   │   │   └── SweetShopServerApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     # Test files
├── pom.xml                       # Maven dependencies
└── README.md
```

## Technology Stack

- **Spring Boot 3.5.7** - Main framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations
- **MySQL** - Database
- **JWT** - Token-based authentication
- **Lombok** - Reduce boilerplate code
- **ModelMapper** - Object mapping
- **Maven** - Build tool

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `POST /api/auth/refresh` - Refresh access token

### Sweets Management (Protected)
- `GET /api/sweets` - Get all sweets
- `GET /api/sweets/search` - Search sweets
- `POST /api/sweets` - Add new sweet
- `PUT /api/sweets/{id}` - Update sweet
- `DELETE /api/{id}` - Delete sweet (Admin only)

### Inventory Management (Protected)
- `POST /sweets/{id}/purchase` - Purchase sweet
- `POST /sweets/{id}/restock` - Restock sweet (Admin only)

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Sweets Table
```sql
CREATE TABLE sweets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    category VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0
);
```

## Configuration Details

### Security Configuration
- JWT-based authentication
- CORS enabled for frontend (http://localhost:3000)
- Role-based access control
- Password encryption with BCrypt

### JWT Configuration
- **Access Token Expiry**: 10 minutes
- **Refresh Token Expiry**: 10 hours
- **Secret Key**: Configurable in application.properties

### Database Configuration
- **Connection Pool**: HikariCP (default)
- **DDL Auto**: update (creates/updates tables automatically)
- **Show SQL**: true (for development)

## Development Commands

### Build the project
```bash
mvn clean compile
```

### Run tests
```bash
mvn test
```

### Package as JAR
```bash
mvn clean package
```

### Run packaged JAR
```bash
java -jar target/sweet-shop-server-0.0.1-SNAPSHOT.jar
```

### Skip tests during build
```bash
mvn clean package -DskipTests
```

## Environment Setup

### Development Profile
Create `application-dev.properties`:
```properties
# Development specific settings
spring.jpa.show-sql=true
logging.level.com.sweet_shop_server=DEBUG
```

Run with dev profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production Profile
Create `application-prod.properties`:
```properties
# Production specific settings
spring.jpa.show-sql=false
logging.level.root=WARN
spring.datasource.url=jdbc:mysql://your-prod-server:3306/sweetshop
```

## Testing the API

### Using curl

#### Register a user
```bash
curl -X POST http://localhost:8050/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

#### Login
```bash
curl -X POST http://localhost:8050/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

#### Get all sweets (with token)
```bash
curl -X GET http://localhost:8050/api/sweets \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

#### Add a sweet (with token)
```bash
curl -X POST http://localhost:8050/api/sweets \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -d '{
    "name": "Chocolate Bar",
    "category": "Chocolate",
    "price": 2.50,
    "quantity": 100
  }'
```

## Troubleshooting

### Database Connection Issues
**Error**: `Communications link failure`
**Solution**:
1. Ensure MySQL is running: `sudo systemctl start mysql`
2. Check connection details in application.properties
3. Verify database exists: `SHOW DATABASES;`

### Port Already in Use
**Error**: `Port 8050 was already in use`
**Solution**:
1. Kill process using port: `lsof -ti:8050 | xargs kill -9`
2. Or change port in application.properties

### JWT Token Issues
**Error**: `JWT signature does not match`
**Solution**:
1. Check jwt.secretKey in application.properties
2. Ensure key is at least 256 bits (32 characters)

### Maven Build Issues
**Error**: `Failed to execute goal`
**Solution**:
1. Clean and rebuild: `mvn clean install`
2. Check Java version: `java -version`
3. Update Maven: `mvn -version`

## Sample Data

### Insert Sample Sweets
```sql
INSERT INTO sweets (name, category, price, quantity) VALUES
('Chocolate Bar', 'Chocolate', 2.50, 100),
('Gummy Bears', 'Gummy', 1.99, 150),
('Lollipop', 'Hard Candy', 0.99, 200),
('Marshmallow', 'Soft Candy', 1.49, 80),
('Caramel Candy', 'Caramel', 2.99, 60);
```

### Create Admin User
```sql
-- First register through API, then update role
UPDATE users SET role = 'ADMIN' WHERE email = 'admin@example.com';
```

## Production Deployment

### Build for Production
```bash
mvn clean package -Pprod
```

### Environment Variables
```bash
export SPRING_PROFILES_ACTIVE=prod
export MYSQL_URL=jdbc:mysql://prod-server:3306/sweetshop
export MYSQL_USERNAME=prod_user
export MYSQL_PASSWORD=secure_password
export JWT_SECRET=your-super-secure-jwt-secret-key-here
```

### Docker Deployment (Optional)
Create `Dockerfile`:
```dockerfile
FROM openjdk:21-jre-slim
COPY target/sweet-shop-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8050
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:
```bash
docker build -t sweet-shop-server .
docker run -p 8050:8050 sweet-shop-server
```

## Health Check

### Application Health
```bash
curl http://localhost:8050/actuator/health
```

### Database Connection
```bash
# Check if tables are created
mysql -u root -p sweetshop -e "SHOW TABLES;"
```

## Logs

### View Application Logs
```bash
# If running with Maven
tail -f logs/spring.log

# If running JAR
java -jar target/sweet-shop-server-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
tail -f app.log
```

### Enable Debug Logging
Add to application.properties:
```properties
logging.level.com.sweet_shop_server=DEBUG
logging.level.org.springframework.security=DEBUG
```

## Integration with Frontend

### CORS Configuration
The backend is configured to accept requests from:
- http://localhost:3000 (React dev server)
- http://localhost:5173 (Vite dev server)

### API Base URL
Frontend should use: `http://localhost:8050`

### Authentication Flow
1. Frontend sends login request
2. Backend returns access token + sets refresh token cookie
3. Frontend stores access token in localStorage
4. Frontend sends access token in Authorization header
5. Backend validates token for protected endpoints

## Support

### Common Issues
1. **MySQL not starting**: `sudo systemctl start mysql`
2. **Wrong Java version**: Use Java 21+
3. **Maven not found**: Install Maven 3.6+
4. **Port conflicts**: Change server.port in application.properties

### Useful Commands
```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check MySQL status
sudo systemctl status mysql

# View running processes on port 8050
lsof -i :8050
```

For additional help, check the application logs and ensure all prerequisites are properly installed and configured.