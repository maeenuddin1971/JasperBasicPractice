package com.example.jasperBasicPractice.testPackage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/testThisApp")
    public ResponseEntity<?> testThisApp() {
        return new ResponseEntity<>("App is Working", HttpStatus.OK);
    }

}
