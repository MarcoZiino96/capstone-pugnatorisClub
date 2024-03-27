import { IAbbonamento } from "./i-abbonamento";


export interface IResponseAbbonamento {
  dateResponse: Date;
  message:string;
  response:IAbbonamento[];
}
