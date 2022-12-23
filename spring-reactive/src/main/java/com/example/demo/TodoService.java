package com.example.demo;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @SneakyThrows
    public Flux<? extends Object> getTodos(int size, int sleep, boolean transform, boolean skipData) {
        if (skipData) {
            return delay(sleep).map((i) -> List.of()).flatMapMany(Flux::fromIterable);
        }

        return this.todoRepository.findWithLimit(size)
            .collectList()
            .flatMap(todos -> this.delay(sleep).map((i) -> todos))
            .map(todos -> {
                if (transform) {
                    return TransformedTodo.transformTodos(todos);
                }

                return todos;
            })
            .flatMapMany(Flux::fromIterable);
    }

    public Mono<Integer> delay(int sleep) {
        return Mono.just(sleep).delaySubscription(Duration.ofMillis(sleep));
    }
}
