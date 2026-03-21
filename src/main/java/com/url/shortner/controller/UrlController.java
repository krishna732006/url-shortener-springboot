package com.url.shortner.controller;

import com.url.shortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UrlController {

    @Autowired
    private UrlService urlService;

    // Open URL Shortener Page
    @GetMapping("/home")
    public String homePage() {
        return "index"; // loads templates/index.html
    }

    // Shorten URL
    @GetMapping("/shorten")
    @ResponseBody
    public String shortenUrl(@RequestParam String longUrl,
                             @RequestParam(required = false) String custom,
                             @RequestParam(required = false) Integer days) {

        return urlService.shortenUrl(longUrl, custom, days);
    }

    // Redirect Short URL
    @GetMapping("/{shortCode}")
    public String redirect(@PathVariable String shortCode) {
        return "redirect:" + urlService.getOriginalUrl(shortCode);
    }

    // Analytics (click count)
    @GetMapping("/analytics/{shortCode}")
    @ResponseBody
    public String analytics(@PathVariable String shortCode) {
        return String.valueOf(urlService.getClickCount(shortCode));
    }
}