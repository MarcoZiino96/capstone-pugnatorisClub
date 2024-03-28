import { Component } from '@angular/core';
import { AuthService } from '../../../Services/auth.service';

@Component({
  selector: 'app-chi-siamo',
  templateUrl: './chi-siamo.component.html',
  styleUrl: './chi-siamo.component.scss'
})
export class ChiSiamoComponent {

  images = [
    "../../../../assets/img/palestra1.jpg",
    "../../../../assets/img/palestra2.jpg",
    "../../../../assets/img/palestra3.jpg",
    "../../../../assets/img/palestra4.jpg",
    "../../../../assets/img/palestra5.jpg",
    "../../../../assets/img/palestra6.jpg",
    "../../../../assets/img/palestra7.jpg"
  ];

  isLoggedIn$!:boolean;

  constructor(
    private authSvc:AuthService
  ){}

  ngOnInit(){
    this.authSvc.isLoggedIn$.subscribe(data=> this.isLoggedIn$ = data)
  }

}
