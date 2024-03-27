import { ICorso } from "../interfaceCorso/i-corso";
import { ITurno } from "../interfaceTurno/i-turno";
import { IUser } from "../interfaceUtente/i-user";

export interface IPrenotazione {
  id:number;
  corso:ICorso;
  utente:IUser|null;
  dataPrenotazione:Date;
  dataScadenza:Date;
  turno:ITurno;
}
