import { ICorso } from "./i-corso";

export interface IRespSingleCorso {
  dateResponse: Date;
  message:string;
  response:ICorso;
}
