export interface TodoDto {
  id: number;
  value: string;
  done: boolean;
  parsed: string;
  totalAmountOfWork: number;
  workDone: number;
  percentageOfWorkDone: number;
}
