import { ITurno } from "./i-turno";

export interface IResponseTurno{
  dateResponse: Date;
  message:string;
  response:ITurno[];
}
