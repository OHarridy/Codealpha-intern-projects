package com.firstconnection.testproject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/urls")
public class controller {
    

    private final Map<String, String> urlMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(controller.class);


    @PostMapping("/shorten")
    public String createShortenedUrl(@ModelAttribute("originalUrl") String originalUrl, Model model) {
        logger.info("hello: {}", originalUrl);
        String shortCode =  encode(originalUrl);
        urlMap.put(shortCode, originalUrl);
        logger.info("{} {}", shortCode, originalUrl);
        model.addAttribute("shorten","http://localhost:8080/api/urls/" +shortCode);
        return "index";
    }


    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable("shortCode") String shortCode) {
       
        String songc= shortCode.substring(Math.max(0, shortCode.length() - 6));
    
        System.out.println("Redirect method called for shortCode: " + songc);
        String originalURL = urlMap.get(songc);
        System.out.println(originalURL);
    
        if (originalURL != null) {
            return ResponseEntity.status(302).location(java.net.URI.create(originalURL)).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String encode( String originalURL) {
        try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(originalURL.getBytes());
        String encodedHash = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        return encodedHash.substring(0, 6); 
        } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        return null;
    }

    }
}
