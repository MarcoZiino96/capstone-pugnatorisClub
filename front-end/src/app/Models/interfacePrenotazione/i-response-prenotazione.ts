import { IPrenotazione } from "./i-prenotazione";

export interface IResponsePrenotazione {
  dateResponse: Date|null;
  message:string;
  response:IPrenotazione[];
}
