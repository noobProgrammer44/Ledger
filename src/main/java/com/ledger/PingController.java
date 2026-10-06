package com.ledger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



// @RestController goes on the class. It tells Spring: "this class handles web requests,
//  and whatever its methods return should be sent straight back as the response."

//Automatically serializes return objects into JSON or XML directly in the HTTP response.
@RestController
public class PingController {


    // @GetMapping("/ping") goes on a method. It tells Spring: "when someone sends a
    // GET request to /ping, run this method." It's the same as app.get("/ping",
    // ...).
    @GetMapping ("/ping")
    public String ping(){
        return "pong";
    }


    @GetMapping("/helloWorld")
    public String getMethodName() {
        return new String("Hello World");
    }
}
