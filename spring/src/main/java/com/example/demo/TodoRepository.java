package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    @Query(value = "SELECT * from todos LIMIT ?1", nativeQuery = true)
    List<Todo> findWithLimit(int limit);
}
