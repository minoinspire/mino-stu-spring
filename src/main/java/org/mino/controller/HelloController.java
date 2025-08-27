package org.mino.controller;

import org.mino.model.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public ApiResponse<String> hello() {
        return ApiResponse.success("Hello, Spring Boot!");
    }

    @GetMapping("/hello/{name}")
    public ApiResponse<String> helloWithName(@PathVariable String name) {
        return ApiResponse.success("Hello, " + name + "! Welcome to Spring Boot!");
    }

    @GetMapping("/info")
    public ApiResponse<Map<String, Object>> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("projectName", "Mino Spring Boot 学习项目");
        info.put("version", "1.0.0");
        info.put("description", "这是一个采用接口形式实现的Spring Boot RESTful API示例1");
        info.put("features", new String[]{"用户管理", "RESTful API", "分层架构", "接口设计"});
        
        return ApiResponse.success("项目信息查询成功", info);
    }
}
