import {Module} from '@nestjs/common';
import {AppController} from './app.controller';
import {AppService} from './app.service';
import {TypeOrmModule} from "@nestjs/typeorm";
import {Todo} from "./todo.entity";
import {config} from "./ormconfig";

@Module({
  imports: [
    TypeOrmModule.forRoot(config),
    TypeOrmModule.forFeature([Todo])
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {
}
