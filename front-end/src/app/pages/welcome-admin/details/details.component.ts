import { AbbonamentoService } from './../../../Services/abbonamento-service';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from './../../../Services/auth.service';
import { Component, Inject } from '@angular/core';
import { IAbbonamento } from '../../../Models/interfaceAbbonamento/i-abbonamento';
import { IUser } from '../../../Models/interfaceUtente/i-user';
import { IResponsePrenotazione } from '../../../Models/interfacePrenotazione/i-response-prenotazione';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrl: './details.component.scss'
})
export class DetailsComponent {

  abbonamenti!: IAbbonamento[];
  user!: IUser;
  showAbbonamenti!: boolean;
  showPrenotazioni!: boolean;
  myPrenotazioni!: IResponsePrenotazione

  constructor(
    private authSvc: AuthService,
    private abbonamentoSvc: AbbonamentoService,
    private route: ActivatedRoute,
    @Inject('Swal') private swal: any
  ) { }

  ngOnInit() {

    this.route.params.subscribe((params: any) => {
      this.authSvc.getById(params.id).subscribe((res) => {
        this.user = res.response
      })
    })

    this.route.params.subscribe((params: any) => {
      this.authSvc.getAbbonamenti(params.id).subscribe((res) => {
        if (res.response.length === 0) {
          this.showAbbonamenti = false
        } else {
          this.abbonamenti = res.response
          this.showAbbonamenti = true
        }

      })
      this.authSvc.getPrenotazioni(params.id).subscribe((res => {
        if (!res) {
          this.swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Problemi di comunicazione con il server, controlla la tua conessione"
          });
        }
        else if (res.response.length === 0) {
          this.showPrenotazioni = false
        } else {
          this.myPrenotazioni = res
          this.showPrenotazioni = true
        }
      }))
    })

  }

  deleteAbbonamento(id: number) {

    this.swal.fire({
      title: "Sei sicuro?",
      text: "Premi su Chiudi per tornare indietro!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#C65535",
      cancelButtonText: "Chiudi",
      confirmButtonText: "Si, voglio cancellarlo!"

    }).then((result: { isConfirmed: boolean }) => {
      if (result.isConfirmed) {
        this.abbonamentoSvc.delete(id).subscribe(() => {
          this.abbonamenti = this.abbonamenti.filter(res => res.id !== id)
          this.swal.fire({
            title: "Good job!",
            text: "Abbonamento eliminato con  successo!",
            icon: "success",
            confirmButtonText: "Chiudi"
          })
        })
      }
    })
  }
}


