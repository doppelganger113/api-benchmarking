package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/api/todos")
    public List<? extends Object> getTodos(
        @RequestParam(required = false) Optional<Integer> size,
        @RequestParam(required = false) Optional<Integer> sleep,
        @RequestParam(required = false) Optional<Boolean> transform,
        @RequestParam(required = false) Optional<Boolean> skipData
    ) {
        return this.todoService.getTodos(
            size.orElse(20),
            sleep.orElse(0),
            transform.orElse(false),
            skipData.orElse(false)
        );
    }
}
