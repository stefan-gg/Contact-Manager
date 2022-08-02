package com.ing.contactmanager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello_world")
public class HelloWorldController {

    @GetMapping
    public ResponseEntity<String> getHelloWorld(){
        return new ResponseEntity<String>("Hello World", HttpStatus.OK);
    }
}
