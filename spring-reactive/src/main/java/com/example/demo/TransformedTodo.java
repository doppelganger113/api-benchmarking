package com.example.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransformedTodo extends Todo {
    private String Parsed;
    private Double PercentageOfWorkDone;

    public static List<TransformedTodo> transformTodos(List<Todo> todos) {
        var size = todos.size();

        return todos.stream()
            .map(todo -> fromTodo(todo, size))
            .collect(Collectors.toList());
    }

    public static TransformedTodo fromTodo(Todo todo, int todosLength) {
        var transformed = new TransformedTodo();
        transformed.setId(todo.getId());
        transformed.setDone(todo.isDone());
        transformed.setValue(todo.getValue());
        transformed.setWorkDone(todo.getWorkDone());
        transformed.setParsed(parseTodo(todo, todosLength));
        transformed.setTotalAmountOfWork(todo.getTotalAmountOfWork());

        var percentage = Math.floor(
            ((todo.getWorkDone() / (double) todo.getTotalAmountOfWork()) * 100)
        );
        transformed.setPercentageOfWorkDone(percentage);

        return transformed;
    }

    public static String parseTodo(Todo todo, int todosLength) {
        var doneStr = "Not done";
        if (todo.isDone()) {
            doneStr = "Done";
        }
        return String.format("Value length is: %d and it's %s", todosLength, doneStr);
    }
}
