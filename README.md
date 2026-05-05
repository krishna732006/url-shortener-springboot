# URL Shortener — Spring Boot

A full-stack URL shortening service built with Spring Boot, H2 (in-memory DB), and a simple HTML/CSS frontend. Deployed live on Render.

## Features
- Generates unique short codes for any long URL
- Redirects users from short URL to the original
- Tracks how many times each link has been clicked
- Lightweight frontend served via Thymeleaf

## Tech Stack
- Java 17, Spring Boot
- Spring Data JPA, H2 Database
- Thymeleaf, HTML/CSS
- Deployed on Render

## Running Locally
```bash
git clone https://github.com/krishna732006/url-shortener-springboot
cd url-shortener-springboot
./mvnw spring-boot:run
```
Open `http://localhost:8080` in your browser.
