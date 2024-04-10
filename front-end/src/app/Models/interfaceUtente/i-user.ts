export interface IUser {
  id:number;
  password: string;
  ruolo: string;
  nome: string;
  cognome: string;
  email: string;
  username: string;
  fotoProfilo: File | null;
  dataNascita: Date | null;
}
