import * as path from 'path';
import {Todo} from "./todo.entity";
import {TypeOrmModuleOptions} from "@nestjs/typeorm";

export const config: TypeOrmModuleOptions = {
  type: 'postgres',
  url: process.env.DB_URL || 'postgresql://user:1234@localhost:5432/test?sslmode=disable',
  entities: [
    Todo,
  ],
  synchronize: true,
  migrations: [path.resolve(__dirname, 'migrations/**/*.ts')]
}
