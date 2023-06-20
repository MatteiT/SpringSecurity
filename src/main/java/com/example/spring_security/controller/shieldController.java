package com.example.spring_security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class shieldController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to the SHIELD";
    }

    @RequestMapping("/avengers/assemble")
    @PreAuthorize("hasAuthority('SHIELD Hero')")
    public String assemble() {
        return "Avengers..... Assemble";
    }

    @RequestMapping("/secret-bases")
    @PreAuthorize("hasAuthority('SHIELD Director')")
    public String secretBases() {
        return "Here is the list of Wild Code Schools' cities";
    }

}