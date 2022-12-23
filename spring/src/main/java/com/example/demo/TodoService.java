package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public List<? extends Object> getTodos(int size, int sleep, boolean transform, boolean skipData) {
        List<Todo> todos = List.of();

        if (!skipData) {
            todos = this.todoRepository.findWithLimit(size);
            if (transform) {
                return TransformedTodo.transformTodos(todos);
            }
        }

        if (sleep > 0) {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return todos;
    }
}
