package com.training.greetapi.basicgreet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MVCController {
    private static final Logger logger = LoggerFactory.getLogger(MVCController.class);

    @GetMapping("/home")
    public String home() {
        return "home.html";
    }


    /*
    * When the application makes the request, it
    expects that the response has an Access-Control-Allow-Origin header contain-
    ing the origins accepted by the server. If this doesn’t happen, the browser won’t
    * accept the response
    *
    * */


    @PostMapping("/test")
    @ResponseBody
    //@CrossOrigin(origins = {"http://localhost:8080"})
    public String test() {
        logger.info("Test method called");
        return "HELLO";
    }
}
