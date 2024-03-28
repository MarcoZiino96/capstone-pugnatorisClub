import { Component } from '@angular/core';
import { AuthService } from '../../../Services/auth.service';

@Component({
  selector: 'app-maestri',
  templateUrl: './maestri.component.html',
  styleUrl: './maestri.component.scss'
})
export class MaestriComponent {

  isLoggedIn$!:boolean;

  constructor(
    private authSvc:AuthService
  ){}

  ngOnInit(){
    this.authSvc.isLoggedIn$.subscribe(data=> this.isLoggedIn$ = data)
  }

}
