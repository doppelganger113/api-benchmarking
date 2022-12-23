package main

import (
	"context"
	"fmt"
	"github.com/jackc/pgx/v4/pgxpool"
	"math"
)

type Todo struct {
	Id                int    `json:"id"`
	Value             string `json:"value"`
	Done              bool   `json:"done"`
	TotalAmountOfWork int64  `json:"totalAmountOfWork"`
	WorkDone          int64  `json:"workDone"`
}

type TodoTransformed struct {
	Todo
	Parsed               string  `json:"parsed"`
	PercentageOfWorkDone float64 `json:"percentageOfWorkDone"`
}

type Database struct {
	pool *pgxpool.Pool
}

func (d *Database) Close() {
	d.pool.Close()
}

func NewDB(connectionUrl string) (*Database, error) {
	dbpool, err := pgxpool.Connect(context.Background(), connectionUrl)

	return &Database{pool: dbpool}, err
}

func (d *Database) FetchTodos(ctx context.Context, size int) ([]Todo, error) {
	rows, err := d.pool.Query(
		ctx,
		"SELECT id, value, done, total_amount_of_work, work_done FROM todos LIMIT $1",
		size,
	)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var todos []Todo
	for rows.Next() {
		var todo Todo
		err = rows.Scan(
			&todo.Id, &todo.Value, &todo.Done, &todo.TotalAmountOfWork, &todo.WorkDone,
		)
		if err != nil {
			return nil, err
		}
		todos = append(todos, todo)
	}

	if todos == nil {
		return make([]Todo, 0), err
	}

	return todos, nil
}

func transformTodos(todos []Todo) []TodoTransformed {
	var todosTransformed []TodoTransformed

	for i := range todos {
		transformed := TodoTransformed{
			Todo:                 todos[i],
			Parsed:               parseTodo(todos[i], len(todos)),
			PercentageOfWorkDone: math.Floor(float64((todos[i].WorkDone / todos[i].TotalAmountOfWork) * 100)),
		}
		todosTransformed = append(todosTransformed, transformed)
	}

	return todosTransformed
}

func parseTodo(todo Todo, todosLength int) string {
	var done string
	if todo.Done {
		done = "Done"
	} else {
		done = "Not done"
	}
	return fmt.Sprintf("Value length is: %d and it's %s", todosLength, done)
}
