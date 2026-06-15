package com.example;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    final NamedParameterJdbcTemplate jdbcTemplate;

    HelloController(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/")
    String hello() {
        return "Hello World!";
    }

    @GetMapping("/users")
    List<Map<String, Object>> getUsers() {
        String sql = "SELECT * FROM users";
        try {
            return jdbcTemplate.queryForList(sql, Map.of());
        } catch (Exception e) {
            return List.of(
                Map.of(
                    "error",
                    "Users table not found or database error",
                    "detail",
                    e.getMessage()
                )
            );
        }
    }
}
