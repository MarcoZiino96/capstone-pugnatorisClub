import { Component, Inject } from '@angular/core';
import { IUser } from '../../../Models/interfaceUtente/i-user';
import { AuthService } from '../../../Services/auth.service';
import { CorsoService } from '../../../Services/corso.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ICorso } from '../../../Models/interfaceCorso/i-corso';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AbbonamentoService } from '../../../Services/abbonamento-service';
import { catchError } from 'rxjs';
import { ISendAbbonamento } from '../../../Models/interfaceAbbonamento/i-send-abbonamento';
import { ChangeRole } from '../../../Models/interfaceUtente/change-role';


@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrl: './create.component.scss'
})
export class CreateComponent{

  iUser: IUser={
    id: 0,
    password: '',
    ruolo: '',
    nome: '',
    cognome: '',
    email: '',
    username: '',
    fotoProfilo: null,
    dataNascita: null,
  };

  iCorso!:ICorso[];
  abbonamento:ISendAbbonamento ={
    corso:0,
    utente:0,
    durata:""
  };

  changeRole:ChangeRole={
    ruolo:""
  }

  constructor(
  private corsoSvc: CorsoService,
  private authSvc: AuthService,
  private abbonamentotSvc: AbbonamentoService,
  @Inject('Swal') private swal: any,
  private route: ActivatedRoute,
  private fb: FormBuilder,
  private router : Router
  ){}

  ngOnInit(){

    this.route.params.subscribe((params: any) =>{
      this.authSvc.getById(params.id).subscribe((res =>{
        if (!res) {
          this.swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Nessun utente trovato"
          });
        }
        this.iUser = res.response
      }))
    })
    this.corsoSvc.getAll().subscribe((corsi =>{
    this.iCorso = corsi.response
    }))
  }

  formAbbonamento: FormGroup = this.fb.group({
    selectedCorso: ["", Validators.required],
    selectedDurata: ["", Validators.required],
  })

  formRuolo: FormGroup = this.fb.group({
    selectedRuolo: ["", Validators.required]
  })



  addAbbonamento(){

    const selectedCorsoId = this.formAbbonamento.get('selectedCorso')?.value;
    const selectedDurata = this.formAbbonamento.get('selectedDurata')?.value;

    this.abbonamento.durata = selectedDurata;
    this.abbonamento.corso = selectedCorsoId;
    this.abbonamento.utente = this.iUser.id;

    this.abbonamentotSvc.create(this.abbonamento)
    .pipe((catchError(error => {
      if (error.error.message === "Questo utente ha gia una abbonamento per questo corso" ) {
        this.swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Questo utente ha gia una abbonamento per questo corso",
          confirmButtonText: "Chiudi"
        })
      } if (error.error.message === "Il corso ha raggiunto il numero massimo di partecipanti") {
        this.swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Il corso ha raggiunto il numero massimo di partecipanti",
          confirmButtonText: "Chiudi"
        })
      }
      throw error;
    }))).subscribe((data) => {
      if (data) {
        this.swal.fire({
          title: "Good job!",
          text: "Abbonamento aggiunto con successo!",
          icon: "success",
          confirmButtonText: "Chiudi"
        })
        this.router.navigate(['../../welcomeAdmin'])
      } else {
        this.swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Qualcosa è andato storto",
          confirmButtonText: "Chiudi"
        })
      }
    })
  }

  updateRole(){

    const selectedRuolo = this.formRuolo.get('selectedRuolo')?.value;
    this.changeRole.ruolo = selectedRuolo

    this.authSvc.uploadRole(this.iUser.id, this.changeRole)
    .pipe((catchError(error =>{
      if(error.error.message === "Questo utente ha già questo ruolo."){
        this.swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Questo utente ha già questo ruolo",
          confirmButtonText: "Chiudi"
        })
      }
      throw error;
    })))
    .subscribe((res)=>{
     if (res) {
        this.swal.fire({
          title: "Good job!",
          text: "Ruolo cambiato con successo!",
          icon: "success",
          confirmButtonText: "Chiudi"
        })
        this.iUser.ruolo = selectedRuolo
      }
    })
  }
}
