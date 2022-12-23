import * as fs from 'fs';
import * as path from 'path';
import {MigrationInterface, QueryRunner} from "typeorm";
import {Todo} from "../src/todo.entity";

export class DataSeed1625869406587 implements MigrationInterface {

  public async up(queryRunner: QueryRunner): Promise<void> {
    const data = fs.readFileSync(path.resolve(`${__dirname}/todos.json`));
    const todos = JSON.parse(String(data));

    for (const todo of todos) {
      const {id, value, done, totalAmountOfWork, workDone} = todo;
      await queryRunner.manager.save(Todo, {
        id,
        value,
        done,
        totalAmountOfWork,
        workDone
      })
    }
  }

  public async down(queryRunner: QueryRunner): Promise<void> {
    await queryRunner.manager.clear(Todo)
  }
}
