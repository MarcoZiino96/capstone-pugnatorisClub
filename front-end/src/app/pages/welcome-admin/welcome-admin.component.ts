
import { Component, Inject } from '@angular/core';
import { AuthService } from '../../Services/auth.service';
import { ICompleteUser } from '../../Models/interfaceUtente/i-complete-user';
import { AbbonamentoService } from '../../Services/abbonamento-service';

@Component({
  selector: 'app-welcome-admin',
  templateUrl: './welcome-admin.component.html',
  styleUrl: './welcome-admin.component.scss'
})
export class WelcomeAdminComponent {

  iUsers!:ICompleteUser[]

  constructor(
    private authSvc:AuthService,
    @Inject('Swal') private swal: any,
    private abbonamentoSvc:AbbonamentoService
    ){}


  ngOnInit(){
    this.authSvc.getAllUtenti().subscribe((users) =>{
      this.iUsers = users.response.filter(user => user.ruolo !== "ADMIN");
    })
  }

  deleteUtente(id:number){

    this.swal.fire({
      title: "Sei sicuro?",
      text: "Premi su Chiudi per tornare indietro!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d33",
      cancelButtonColor: "#C65535",
      cancelButtonText:"Chiudi",
      confirmButtonText: "Si, voglio cancellarlo!"

    }).then((result: { isConfirmed: boolean }) => {
      if (result.isConfirmed) {
        this.authSvc.deleteUtente(id).subscribe(()=>{
          this.iUsers = this.iUsers.filter(user => user.id !== id)
          this.swal.fire({
          title: "Cancellato!",
          text: "Utente cancellato corettamente.",
          icon: "success"
        });
        })
      }
    })
  }

  aggiornaDataBase(){
    this.abbonamentoSvc.aggiornaDataBase().subscribe((res)=>{
      if(res.message === "Il database è stato aggiornato"){
        this.swal.fire({
          title: "Aggiornato!",
          text: "Database aggiornato corettamente.",
          icon: "success"
        });
      }else{
        this.swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Qalcosa è andato storto, riprova"
        });
      }
    })
  }
}
