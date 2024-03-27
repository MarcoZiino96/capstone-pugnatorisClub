import { ICompleteUser } from "./i-complete-user";
import { IUser } from "./i-user";

export interface IResponseData {
  dateResponse: Date;
  message:string;
  response:IUser;
}
