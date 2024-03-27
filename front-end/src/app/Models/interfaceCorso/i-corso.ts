import { Insegnante } from "../interfaceInsegnante/insegnante";

export interface ICorso {
  id: number;
  categoria: string;
  descrizione: string;
  numeroMassimoPartecipanti: number;
  costoMensile: number;
  durata: string;
  maestro:Insegnante|null;
}
