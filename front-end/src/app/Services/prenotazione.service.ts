import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { IResponsePrenotazione } from '../Models/interfacePrenotazione/i-response-prenotazione';
import { environment } from '../../environments/environment.development';
import { ISendPrenotazione } from '../Models/interfacePrenotazione/i-send-prenotazione';

@Injectable({
  providedIn: 'root'
})
export class PrenotazioneService {
  backendUrl: string = `${environment.backEndUrl}`;


  constructor(private http:HttpClient, @Inject('Swal') private swal: any) { }

  getById(id:number):Observable<IResponsePrenotazione>{
    return this.http.get<IResponsePrenotazione>(`${environment.backEndUrl}/prenotazione/${id}`)
    .pipe(catchError(error=>{
      if (error.error.message === "Turno non trovato" ){
        this.swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Turno non trovato"
        });
      }
      throw error;
    }))
  }

  sendPrenotazione(prenotazione:ISendPrenotazione):Observable<IResponsePrenotazione>{
    return this.http.post<IResponsePrenotazione>(`${this.backendUrl}/prenotazione/create`, prenotazione)
  }

  deletePrenotazione(id:number):Observable<void>{
   return  this.http.delete<void>(`${this.backendUrl}/prenotazione/delete/${id}`)
  }
}
