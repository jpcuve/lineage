package com.messio.lineage;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jpc on 31-05-17.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }
}
