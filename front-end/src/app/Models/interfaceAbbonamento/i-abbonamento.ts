import { ICorso } from './../interfaceCorso/i-corso';
import { ICompleteUser } from "../interfaceUtente/i-complete-user";

export interface IAbbonamento {
  id:number;
  durata:string;
  corso:ICorso;
  utente:ICompleteUser;
  dataAttivazione:string;
  dataScadenza:string;
  costoAbbonamento:number;
}

