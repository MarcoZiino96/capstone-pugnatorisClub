import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { ISendAbbonamento } from '../Models/interfaceAbbonamento/i-send-abbonamento';
import { IResponseAbbonamento } from '../Models/interfaceAbbonamento/i-response-abbonamento';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AbbonamentoService{

  backendUrl: string = `${environment.backEndUrl}`;

  constructor(private http:HttpClient) {}

  create(abbonamento:ISendAbbonamento){
    return this.http.post<IResponseAbbonamento>(`${this.backendUrl}/abbonamento/create`, abbonamento);
  }

  delete(id:number):Observable<void>{
    return this.http.delete<void>(`${this.backendUrl}/abbonamento/delete/${id}`)
  }

  aggiornaDataBase():Observable<IResponseAbbonamento>{
    return this.http.delete<IResponseAbbonamento>(`${this.backendUrl}/abbonamento/delete/dettagli`)
  }
}
