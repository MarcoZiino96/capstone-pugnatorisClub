
import { Component } from '@angular/core';
import { CorsoService } from '../../Services/corso.service';
import { ICorso } from '../../Models/interfaceCorso/i-corso';
import { AuthService } from '../../Services/auth.service';





@Component({
  selector: 'app-welcome-user',
  templateUrl: './welcome-user.component.html',
  styleUrl: './welcome-user.component.scss'
})
export class WelcomeUserComponent {

  iCorsi!:ICorso[];
  accessData!:number|undefined

  constructor(
    private corsoSvc:CorsoService,
    private authSvc:AuthService
    ){}

  ngOnInit(){

    this.authSvc.authSubject.subscribe((data) => {
      this.accessData = data?.user.id
    })

     this.getAllCorsi();
 }

 getAllCorsi(){
  this.corsoSvc.getAll().subscribe(data =>{
   return  this.iCorsi = data.response
  })
 }

 getImgByCategories(categoria:string){
  return this.corsoSvc.getImgByCategories(categoria)
 }
}






