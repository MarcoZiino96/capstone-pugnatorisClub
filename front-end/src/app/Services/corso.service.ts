import { Inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { IResponseCorso } from '../Models/interfaceCorso/i-response-corso';
import { Observable, catchError } from 'rxjs';
import { IRespSingleCorso } from '../Models/interfaceCorso/i-resp-single-corso';


@Injectable({
  providedIn: 'root'
})
export class CorsoService {

  backendUrl: string = `${environment.backEndUrl}`;


  constructor(private http:HttpClient, @Inject('Swal') private swal: any) {}

  getAll(): Observable<IResponseCorso>{
    return this.http.get<IResponseCorso>(`${this.backendUrl}/corso`)
  }

  getById(id:number):Observable<IRespSingleCorso>{
    return this.http.get<IRespSingleCorso>(`${environment.backEndUrl}/corso/${id}`)
    .pipe(catchError(error=>{
      if (error.error.message === "Il corso non è presente" ){
        this.swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Corso non trovato"
        });
      }
      throw error;
    }))
  }

  getImgByCategories(categoria: string): string{

    switch (categoria) {
      case 'BOXE':
        return '../../assets/img/boxe.jpeg';
      case 'KICKBOXING':
        return '../../assets/img/kick-boxing.jpeg';
      case 'MUAY_THAI':
        return '../../assets/img/muay-thay.jpeg';
      case 'MMA_GRAPPLING':
        return '../../assets/img/mma-grappling.jpeg';
      default:
        return 'https://picsum.photos/200/300?random=1';
    }
  }

}
