package com.example.demo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TodoRepository extends ReactiveCrudRepository<Todo, Integer> {
    @Query(value = "SELECT * from todos LIMIT :limit")
    Flux<Todo> findWithLimit(int limit);
}
