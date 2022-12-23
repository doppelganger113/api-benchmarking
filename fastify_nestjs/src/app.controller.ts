import {Controller, Get, Query} from '@nestjs/common';
import {AppService} from './app.service';
import {TodoDto} from "./todo.dto";
import {Todo} from "./todo.entity";
import {InjectRepository} from "@nestjs/typeorm";
import {Repository} from "typeorm";

const transformTodos = (todos: Todo[]): TodoDto[] => {
  return todos.map(todo => ({
    ...todo,
    parsed: `Value length is: ${todo.value.length} and it's ${todo.done ? 'Done' : 'Not done'}`,
    percentageOfWorkDone: Math.floor((todo.workDone / todo.totalAmountOfWork) * 100)
  }));
}

const sleepMillis = (millis: number): Promise<void> =>
  new Promise((resolve) => {
    setTimeout(resolve, millis)
  })

@Controller()
export class AppController {
  constructor(
    private readonly appService: AppService,
    @InjectRepository(Todo)
    private todosRepository: Repository<Todo>,
  ) {
  }

  @Get()
  getHello(): string {
    return this.appService.getHello();
  }

  @Get("/api/todos")
  async getTodos(
    @Query('sleep') sleep: number = 0,
    @Query('size') size: number = 20,
    @Query('transform') transform = false,
    @Query('skipData') skipData = false,
  ): Promise<TodoDto[] | Todo[]> {
    let results = [];

    if(!skipData) {
      const todos = await this.fetchTodos(size);

      if (transform) {
        results = transformTodos(todos);
      } else {
        results = todos
      }
    }

    // We intentionally want to sleep after retrieving the data
    if (sleep > 0) {
      await sleepMillis(sleep)
    }

    return results;
  }

  private fetchTodos(size: number): Promise<Todo[]> {
    return this.todosRepository.find({take: size})
  }
}
