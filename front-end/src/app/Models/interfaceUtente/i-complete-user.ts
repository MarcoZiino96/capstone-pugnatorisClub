export interface ICompleteUser {
  id:number;
  password: string;
  ruolo: string;
  nome: string;
  cognome: string;
  email: string;
  fotoProfilo: string| null;
  dataNascita: Date | null;
  username:string;
}
