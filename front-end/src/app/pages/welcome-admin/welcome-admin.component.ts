
import { Component, Inject } from '@angular/core';
import { AuthService } from '../../Services/auth.service';
import { ICompleteUser } from '../../Models/interfaceUtente/i-complete-user';
import { AbbonamentoService } from '../../Services/abbonamento-service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-welcome-admin',
  templateUrl: './welcome-admin.component.html',
  styleUrl: './welcome-admin.component.scss'
})
export class WelcomeAdminComponent {

  originalUsers!:ICompleteUser[]
  iUsers!:ICompleteUser[];
  searchUtenteForm!:FormGroup;
  loader: boolean = false;

  constructor(
    private authSvc:AuthService,
    @Inject('Swal') private swal: any,
    private abbonamentoSvc:AbbonamentoService,
    private fb:FormBuilder
    ){}


  ngOnInit(){
    this.searchUtenteForm = this.fb.group({
      cercaUtente:this.fb.control("",[Validators.required])
    })

    this.authSvc.getAllUtenti().subscribe((users) => {
      this.originalUsers = users.response;
      this.iUsers = this.originalUsers;
    });

    this.searchUtenteForm.get('cercaUtente')?.valueChanges.subscribe(value=>{
      this.searchUsers(value)
    })

  }

  searchUsers(string:string){
    if(!string){
      this.iUsers = this.originalUsers
    }else{
    this.iUsers = this.originalUsers.filter(user =>{
    return  user.cognome.trim().toLowerCase().includes(string.trim().toLowerCase())})
    }
  }

  deleteUtente(id:number){
    this.loader = true;
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
          this.originalUsers = this.originalUsers.filter(user => user.id !== id)
          this.swal.fire({
          title: "Cancellato!",
          text: "Utente cancellato corettamente.",
          icon: "success"
        });
        })
      }
    })
    this.loader = false
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
