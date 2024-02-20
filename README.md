# BookLendApp
BookLendApp is a simple Java Spring application that allows users to manage and borrow books. It uses an H2 in-memory database, Spring Web, JPA, Flyway for database migrations, and provides RESTful APIs for book management and user interaction.
# How to Run
1. Make sure you have Docker installed on your system.
2. Pull the Docker image from my Docker Hub repository.
   ```bash
   docker pull saiq123/booklend
4. Execute the Docker container to run the application.
   ```bash
   docker run -p 8080:8080 saiq123/booklend
## Demo
Check out the Swagger documentation for the deployed application on Azure:
[BookLendApp Swagger Demo](https://booklendapp.azurewebsites.net/swagger-ui/index.html#/)
