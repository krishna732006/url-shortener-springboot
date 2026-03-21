package com.url.shortner.controller;

import com.url.shortner.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // Create short URL
    @GetMapping("/shorten")
    public String shortenUrl(@RequestParam String longUrl,
                             @RequestParam(required = false) String custom,
                             @RequestParam(required = false) Integer days) {

        String shortCode = urlService.shortenUrl(longUrl, custom, days);

        if (shortCode.equals("Custom URL already taken!")) {
            return shortCode;
        }

        return "http://localhost:8080/r/" + shortCode;
    }

    // Redirect
    @GetMapping("/r/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String originalUrl = urlService.getOriginalUrl(shortUrl);

        if (originalUrl == null) {
            response.getWriter().write("Short URL not found!");
        } else if (originalUrl.equals("This link has expired!")) {
            response.getWriter().write(originalUrl);
        } else {
            response.sendRedirect(originalUrl);
        }
    }

    // Analytics
    @GetMapping("/analytics/{shortUrl}")
    public int getAnalytics(@PathVariable String shortUrl) {
        return urlService.getClickCount(shortUrl);
    }
}