import { ICorso } from "../interfaceCorso/i-corso";
import { ITurno } from "../interfaceTurno/i-turno";
import { IUser } from "../interfaceUtente/i-user";

export interface ISendPrenotazione {
  corso:number;
  utente:number;
  turno:number;
}
