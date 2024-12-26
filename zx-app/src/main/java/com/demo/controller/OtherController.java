package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/other")
@Tag(name = "其它控制器", description = "其它控制器哦")
class OtherController {
    // index
    @GetMapping
    @Parameter(description = "Index")
    public String index() {
        return "Hello world!";
    }
}