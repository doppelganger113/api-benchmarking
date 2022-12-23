import * as path from 'path';
import {Todo} from "./todo.entity";
import {TypeOrmModuleOptions} from "@nestjs/typeorm";

export const config: TypeOrmModuleOptions = {
  type: 'postgres',
  url: process.env.DB_URL || 'postgresql://postgres:jhjgahdh3424ghdfsgjhk2432afs@test.cqbz8vdev8iv.eu-central-1.rds.amazonaws.com:5432/test?sslmode=disable',
  entities: [
    Todo,
  ],
  synchronize: true,
  // In case of migrations use:
  migrations: ['migrations/**/*.ts']
  // When running the app use
  // migrations: [path.resolve(__dirname, 'migrations/**/*.ts')]
}
