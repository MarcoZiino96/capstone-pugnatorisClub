import { ISendPrenotazione } from './../../../Models/interfacePrenotazione/i-send-prenotazione';
import { Component, Inject } from '@angular/core';
import { CorsoService } from '../../../Services/corso.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../../Services/auth.service';
import { IUser } from '../../../Models/interfaceUtente/i-user';
import { IRespSingleCorso } from '../../../Models/interfaceCorso/i-resp-single-corso';
import { IResponseTurno } from '../../../Models/interfaceTurno/i-response-turno';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TurnoService } from '../../../Services/turno.service';
import { PrenotazioneService } from '../../../Services/prenotazione.service';
import { catchError } from 'rxjs';
import { IRespSingleTurno } from '../../../Models/interfaceTurno/i-resp-single-turno';

@Component({
  selector: 'app-prenotazione',
  templateUrl: './prenotazione.component.html',
  styleUrl: './prenotazione.component.scss'
})
export class PrenotazioneComponent {


  iUser!: IUser;
  accessData!:number|undefined

  prenotazione: ISendPrenotazione = {
    corso: 0,
    utente: 0,
    turno: 0
  }
  iTurni: IResponseTurno = {
    dateResponse: new Date(),
    message: '',
    response: [
      {
        id: 0,
        giornoLezione: "",
        inizioLezione: null,
        fineLezione: null,
      }
    ]
  }

  iTurno: IRespSingleTurno = {
    dateResponse: new Date(),
    message: '',
    response:
    {
      id: 0,
      giornoLezione: "",
      inizioLezione: null,
      fineLezione: null,
    }

  }

  iCorso: IRespSingleCorso = {
    dateResponse: new Date(),
    message: '',
    response: {
      id: 0,
      categoria: '',
      descrizione: '',
      numeroMassimoPartecipanti: 0,
      costoMensile: 0,
      durata: '',
      maestro: null
    }
  }



  constructor(
    private corsoSvc: CorsoService,
    private authSvc: AuthService,
    @Inject('Swal') private swal: any,
    private fb: FormBuilder,
    private turnoSvc: TurnoService,
    private route: ActivatedRoute,
    private prenotazioneSvc: PrenotazioneService,
    private router:Router
  ) { }

  ngOnInit() {

    this.authSvc.authSubject.subscribe((data) => {
      this.accessData = data?.user.id
    })

    this.route.params.subscribe((params: any) =>{
      if(this.accessData != params.idUtente){
        this.swal.fire({
          icon: "error",
          title: "Non fare il furbo...",
          text: "Nessun utente trovato"
        })
        this.router.navigate(['../../welcomeUser']);
      }else{
      this.authSvc.getById(params.idUtente).subscribe((res =>{
        if (!res) {
          this.swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Nessun utente trovato"
          });
        }
        this.iUser = res.response
      }))
    }
    })

    this.route.params.subscribe((params: any) => {
      this.corsoSvc.getById(params.idCorso).subscribe((res => {
        this.iCorso = res

        this.getTurniCorso()
      }))

    })
  }

  formTurno: FormGroup = this.fb.group({
    selectedTurno: ["", Validators.required]
  })

  getTurniCorso() {
    this.turnoSvc.getTurniCorso(this.iCorso.response.id).subscribe((res) => {
      this.iTurni = res
    })
  }

  sendPrenotazione() {

    const selectTurnoId = this.formTurno.get('selectedTurno')?.value;

      this.prenotazione.turno = selectTurnoId
      this.prenotazione.utente = this.iUser.id
      this.prenotazione.corso = this.iCorso.response.id

      this.prenotazioneSvc.sendPrenotazione(this.prenotazione)
        .pipe((catchError(error => {
          if (error.error.message === "Questo utente ha gia una prenotazione per questo corso") {
            this.swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Hai gia una prenotazione per questo corso"
            })
          } if (error.error.message === "Il corso ha raggiunto il numero massimo di partecipanti") {
            this.swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Il corso ha raggiunto il numero massimo di partecipanti"
            })
          }
          throw error;
        })))
        .subscribe((data) => {
          if (data) {
            this.swal.fire({
              title: "Good job!",
              text: "Lezione prenotata con successo!",
              icon: "success"
            })
            this.router.navigate([`../../welcomeUser/user-details/${this.iUser.id}`])
          } else {
            this.swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Qualcosa è andato storto"
            })
          }
        })
  }

  getImgByCategories(categoria: string) {
    return this.corsoSvc.getImgByCategories(categoria)
  }
}
