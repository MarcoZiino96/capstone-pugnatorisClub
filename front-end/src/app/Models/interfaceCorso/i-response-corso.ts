import { ICorso } from "./i-corso";

export interface IResponseCorso {
  dateResponse: Date;
  message:string;
  response:ICorso[];
}
