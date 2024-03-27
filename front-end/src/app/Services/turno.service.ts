import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { IRespSingleTurno } from '../Models/interfaceTurno/i-resp-single-turno';
import { IResponseTurno } from '../Models/interfaceTurno/i-response-turno';

@Injectable({
  providedIn: 'root'
})
export class TurnoService {
  backendUrl: string = `${environment.backEndUrl}`;


  constructor(private http:HttpClient, @Inject('Swal') private swal: any) { }

  getById(id:number):Observable<IRespSingleTurno>{
    return this.http.get<IRespSingleTurno>(`${environment.backEndUrl}/turno/${id}`)
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
  getTurniCorso(id:number):Observable<IResponseTurno>{
    return this.http.get<IResponseTurno>(`${this.backendUrl}/corso/turni/${id}`)
  }
}
