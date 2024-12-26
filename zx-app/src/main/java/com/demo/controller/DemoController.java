package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/")
@Tag(name = "示例控制器", description = "Demo controller")
class DemoController {

    @GetMapping
    @Parameter(description = "Index")
    public String indexGet() {
        return "Hello App!";
    }

    // index
    @PostMapping
    @Parameter(description = "Index")
    public String index() {
        return "Hello App!";
    }
}