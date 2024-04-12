import { ChangeRole } from './../Models/interfaceUtente/change-role';
import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { IAuthData } from '../Models/interfaceUtente/i-auth-data';
import { IRegister } from '../Models/interfaceUtente/i-register';
import { BehaviorSubject, Observable, catchError, map, tap } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { ILogin } from '../Models/interfaceUtente/i-login';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';
import { IResponseData } from '../Models/interfaceUtente/i-response-data';
import { ChangePassword } from '../Models/interfaceUtente/change-password';
import { IResponsePrenotazione } from '../Models/interfacePrenotazione/i-response-prenotazione';
import { IResponseArrayData } from '../Models/interfaceUtente/i-response-array-data';
import { IResponseAbbonamento } from '../Models/interfaceAbbonamento/i-response-abbonamento';
import { ICompleteUser } from '../Models/interfaceUtente/i-complete-user';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  backendUrl: string = `${environment.backEndUrl}`;



  jwtHelper : JwtHelperService = new JwtHelperService();
  authSubject = new BehaviorSubject<IAuthData | null>(null)
  user$: Observable<IAuthData | null> = this.authSubject.asObservable();
  isLoggedIn$ = this.user$.pipe(map(user => !!user))
  isAdmin$ = this.user$.pipe(
    map(user => !!user && user.user.ruolo === 'ADMIN'));


  constructor(
    private http:HttpClient,
    private router:Router,
    @Inject('Swal') private swal: any) {

    this.restoreUser();

   }



  signUp(register:IRegister):Observable<IAuthData>{
    return this.http.post<IAuthData>(`${this.backendUrl}/auth/register`, register)
  }

  logIn(logInData:ILogin){
    return this.http.post<IAuthData>(`${this.backendUrl}/auth/login`, logInData)
    .pipe(tap(data=>{
      this.authSubject.next(data);
      localStorage.setItem('accessData', JSON.stringify(data));
      this.autoLogout(data.accessToken)
    }))
  }

  autoLogout(jwt:string){
    let expDate =this.jwtHelper.getTokenExpirationDate(jwt) as Date;
    let expMs = expDate.getTime() - new Date().getTime()


  setTimeout(()=> {
    this.logout()
  }, expMs)
  }

  logout(){
    this.authSubject.next(null);
    localStorage.removeItem('accessData');
    this.router.navigate(['/auth/login']);
  }

  restoreUser(){
    const userJson:string|null = localStorage.getItem('accessData');
    if(!userJson) return;

    const accessData:IAuthData = JSON.parse(userJson);
    if(this.jwtHelper.isTokenExpired(accessData.accessToken)) return

    this.autoLogout(accessData.accessToken)
    this.authSubject.next(accessData)
  }

  getById(id:number):Observable<IResponseData>{
    return this.http.get<IResponseData>(`${environment.backEndUrl}/utente/${id}`)
    .pipe(catchError(error=>{
      if (error.error.message === "Utente non trovato" ){
        this.swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Utente non torvato"
        });
      }
      throw error;
    }))
  }

  deleteUtente(id:number):Observable<void>{
    return  this.http.delete<void>(`${this.backendUrl}/utente/delete/${id}`)
  }

  getAllUtenti(): Observable<IResponseArrayData>{
    return this.http.get<IResponseArrayData>(`${this.backendUrl}/utente`)
  }

  edit(id:number,bodyRequest:IRegister):Observable<IResponseData> | undefined{
    return this.http.put<IResponseData>(`${environment.backEndUrl}/utente/edit/${id}`, bodyRequest)
  }

  uploadFile(id:number,file: File):Observable<any>{
    const formData = new FormData();
    formData.append('upload', file);
  return this.http.patch(`${environment.backEndUrl}/utente/upload/${id}`, formData);
  }

  changePassword(id:number, password:ChangePassword):Observable<any>{
    return this.http.patch(`${environment.backEndUrl}/utente/edit/password/${id}`, password);
}

 getPrenotazioni(id:number):Observable<IResponsePrenotazione>{
  return this.http.get<IResponsePrenotazione>(`${environment.backEndUrl}/utente/prenotazioni/${id}`);
}

getAbbonamenti(id:number){
  return this.http.get<IResponseAbbonamento>(`${environment.backEndUrl}/utente/abbonamenti/${id}`);
}

uploadRole(id:number, role:ChangeRole):Observable<ICompleteUser>{
  return this.http.patch<ICompleteUser>(`${environment.backEndUrl}/utente/edit/role/${id}`, role)
}
}
