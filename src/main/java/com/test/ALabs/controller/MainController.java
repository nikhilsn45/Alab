package com.test.ALabs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {

    @GetMapping("/")
    public String upload(){
        return "upload.html";
    }

    @GetMapping("/fetch")
    public String download(){
        return "download.html";
    }
}
