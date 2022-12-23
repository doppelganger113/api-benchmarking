package com.example.demo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("todos")
public class Todo {
    @Id()
    @Column("id")
    private Integer id;

    @Column("value")
    private String value;

    @Column("done")
    private boolean done;

    @Column("total_amount_of_work")
    private Integer TotalAmountOfWork;

    @Column("work_done")
    private Integer WorkDone;
}
