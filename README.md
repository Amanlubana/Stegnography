# Digital Steganography- USING JAVA
Data security is a common issue nowadays and it needs protection from various cyber threats, digital steganography helps in safeguarding it. It offers an approach that hides sensitive information within digital files such as text, images etc. Unlike traditional methods. Steganography doesn’t change the data directly, it provides a high level of security. This method promotes privacy, helps in maintaining data integrity and helps in defending against cyber threats. We have used the concept of steganography to build an application which does steganography in text, image and also handwritten text. Project Overview:-

Our objective is to explore, understand and utilize steganography for securing users data. Steganography conceals sensitive information in ordinary files, keeping it completely secret for the users. Our project stands as a tool for data protection for users by hiding their data like text and images and help in hiding data in plain sight by using encryption and steganography algorithms.

# Project Structure

## Frontend Structure (Thymeleaf html + CSS)
src

└── main

    └── resources
    
        ├── templates
        │   ├── index.html
        │   ├── embed.html
        │   └── extract.html
        │
        ├── static
        │   ├── css
        │   │   └── style.css
        │   │
        │   └── js
        │       └── script.js
        │
        └── application.properties

  ## Backend Structure
src

└── main

    ├── java
    │   └── com
    │       └── example
    │           └── demo
    │               ├── DemoApplication.java
    │               │
    │               ├── config
    │               │   └── SecurityConfig.java
    │               │
    │               ├── controller
    │               │   ├── StegoController.java
    │               │   ├── ViewController.java
    │               │   └── AuthController.java 
    │               │
    │               ├── service
    │               │   ├── StegoService.java
    │               │   ├── CryptoService.java
    │               │   └── UserService.java (optional)
    │               │
    │               ├── repository
    │               │   └── UserRepository.java (optional)
    │               │
    │               ├── entity
    │               │   ├── User.java 
    │               │ 
    │               │
    │               ├── dto
    │               │   ├── LoginRequest.java
    │               │   ├── SignupRequest.java
    │               │   └── StegoRequest.java
    │               │
    │               ├── exception
    │               │   ├── GlobalExceptionHandler.java
    │               │   └── CustomException.java
    │               │
    │               └── util
    │                   └── FileUtil.java
    │
    └── resources
        ├── templates
        │   ├── index.html
        │   ├── embed.html
        │   └── extract.html
        │
        ├── static
        │   ├── css
        │   │   └── style.css
        │   │
        │   └── js
        │       └── script.js
        │
        └── application.properties
# Tech Stack

- Backend: Spring Boot, Spring Security, Spring Data JPA
- Frontend: Thymeleaf, HTML, CSS
- Database: MySQL
- Build Tool: Maven
- Language: Java

# Endpoints

| Method | Endpoint     | Description        |
|--------|-------------|--------------------|
| GET    | /login      | Login page         |
| GET    | /register   | Register page      |
| POST   | /register   | Register user      |
| GET    | /home       | Dashboard          |
| GET    | /embed      | Embed page         |
| GET    | /extract    | Extract page       |

# Validation

- Username: 4–20 characters
- Password: Minimum 6 characters
- Unique username enforced
- Error messages shown on UI
# Author

Amandeep Singh  
Java Full Stack Developer  
