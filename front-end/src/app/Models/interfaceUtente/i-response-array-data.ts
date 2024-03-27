import { ICompleteUser } from "./i-complete-user";


export interface IResponseArrayData {
  dateResponse: Date;
  message:string;
  response:ICompleteUser[];
}
