import {Column, Entity, PrimaryGeneratedColumn} from "typeorm";

@Entity({name: 'todos'})
export class Todo {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  value: string;

  @Column()
  done: boolean

  @Column({name: 'total_amount_of_work'})
  totalAmountOfWork: number;

  @Column({name: 'work_done'})
  workDone: number;
}
