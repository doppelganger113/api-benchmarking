package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Optional;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/api/todos")
    public Flux<? extends Object> getTodos(
        @RequestParam(required = false) Optional<Integer> size,
        @RequestParam(required = false) Optional<Integer> sleep,
        @RequestParam(required = false) Optional<Boolean> transform,
        @RequestParam(required = false) Optional<Boolean> skipData
    ) {
        return todoService.getTodos(
            size.orElse(20), sleep.orElse(0), transform.orElse(false), skipData.orElse(false)
        );
    }
}
