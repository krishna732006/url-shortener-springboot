package com.url.shortner.service;

import com.url.shortner.models.UrlMapping;
import com.url.shortner.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UrlService {

    private final UrlMappingRepository repository;

    public UrlService(UrlMappingRepository repository) {
        this.repository = repository;
    }

    // Create short URL
    public String shortenUrl(String longUrl, String customCode, Integer days) {
        String shortCode;

        // Custom short URL
        if (customCode != null && !customCode.isEmpty()) {
            if (repository.findByShortUrl(customCode).isPresent()) {
                return "Custom URL already taken!";
            }
            shortCode = customCode;
        } else {
            // Generate unique random short code
            do {
                shortCode = generateShortCode();
            } while (repository.findByShortUrl(shortCode).isPresent());
        }

        UrlMapping url = new UrlMapping();
        url.setOriginalUrl(longUrl);
        url.setShortUrl(shortCode);
        url.setCreatedDate(LocalDateTime.now());

        // Expiry date
        if (days != null) {
            url.setExpiryDate(LocalDateTime.now().plusDays(days));
        }

        repository.save(url);
        return shortCode;
    }

    // Redirect logic + click count + expiry check
    public String getOriginalUrl(String shortCode) {
        UrlMapping url = repository.findByShortUrl(shortCode).orElse(null);

        if (url != null) {

            // Check expiry
            if (url.getExpiryDate() != null &&
                    url.getExpiryDate().isBefore(LocalDateTime.now())) {
                return "This link has expired!";
            }

            // Increase click count
            url.setClickCount(url.getClickCount() + 1);
            repository.save(url);

            return url.getOriginalUrl();
        }

        return null;
    }

    // Analytics
    public int getClickCount(String shortCode) {
        return repository.findByShortUrl(shortCode)
                .map(UrlMapping::getClickCount)
                .orElse(0);
    }

    // Random short code generator
    private String generateShortCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder shortCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            shortCode.append(chars.charAt(random.nextInt(chars.length())));
        }

        return shortCode.toString();
    }
}