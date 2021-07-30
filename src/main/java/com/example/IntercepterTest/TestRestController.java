package com.example.IntercepterTest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestRestController {
    @GetMapping("/get")
    public String getRequest(@RequestParam("type") String type, @RequestParam("name") String name) {
        return String.format("Get Request Called! Type = %s Name = %s ", type, name);
    }
    @PostMapping("/post")
    public String postRequest(@RequestBody String body) {
        return "Post Request Called! ";
    }
    @PutMapping("/put")
    public String putRequest(@RequestParam("type") String type, @RequestParam("name") String name) {
        return String.format("Put Request Called! Type = %s Name = %s ", type, name);
    }
    @DeleteMapping("/delete")
    public String deleteRequest(@RequestParam("type") String type, @RequestParam("name") String name) {
        return String.format("Delete Request Called! Type = %s Name = %s ", type, name);
    }
}
