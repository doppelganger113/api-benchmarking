package com.example.demo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "todos")
public class Todo {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value;

    @Column(name = "done")
    private boolean done;

    @Column(name = "total_amount_of_work")
    private Integer TotalAmountOfWork;

    @Column(name = "work_done")
    private Integer WorkDone;
}
