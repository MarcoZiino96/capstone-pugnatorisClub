import { Component, Inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../../Services/auth.service';
import { ILogin } from '../../../Models/interfaceUtente/i-login';
import { catchError } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  msg!:ILogin;
  errorMsg!:ILogin;
  showPassword:boolean=false;
  loader: boolean = false;


  constructor(private fb:FormBuilder, private authSvc:AuthService, private router: Router,@Inject('Swal') private swal: any){}

  loginForm:FormGroup = this.fb.group({

    username: this.fb.control(null,[Validators.required,Validators.minLength(4),Validators.maxLength(16),Validators.pattern(/^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?":{}|<>])(?=.*[a-zA-Z0-9]).{4,}$/)]),
    password: this.fb.control(null,[Validators.required, Validators.minLength(8), Validators.maxLength(16)]),
  });

  ngDoCheck(){
    this.errorMsg = {
      username: this.invalidMessages("username"),
      password: this.invalidMessages('password'),
    }

    this.msg = {
      password: '',
      username: ''
    }

    if (this.errorMsg.username) {
      this.msg.username = this.errorMsg.username
    } else {
      this.msg.username = 'Campo compilato correttamente'
    }
    if (this.errorMsg.password) {
      this.msg.password = this.errorMsg.password
    } else {
      this.msg.password = 'Password corretta'
    }
  }

  invalidMessages(fieldName: string):string{
    const field: AbstractControl | null = this.loginForm.get(fieldName);
    let errorMsg:string = "";

    if(field)
     if(field.errors){

      if (field.errors['required'])
      errorMsg = 'Campo vuoto'

        if (field.errors['minlength'] && fieldName === 'password')
        errorMsg = 'Lunghezza minima password: 8 caratteri'

        if (field.errors['maxlength'] && fieldName === 'password')
        errorMsg = 'Lunghezza massima password: 16 caratteri'

        if (field.errors['minlength'] && fieldName === 'username')
        errorMsg = 'Lunghezza minima password: 4 caratteri'

        if (field.errors['maxlength'] && fieldName === 'username')
        errorMsg = 'Lunghezza massima password: 16 caratteri'

        if (field.errors['pattern'] && fieldName === 'username')
        errorMsg = 'deve contenere almeno una lettere maiuscola e un carattere speciale'

     }
     return errorMsg;
  }

  isValid(inputName:string){
    return this.loginForm.get(inputName)?.valid && this.loginForm.get(inputName)?.dirty
  }

  isInvalid(inputName:string){
    return !this.loginForm.get(inputName)?.valid && this.loginForm.get(inputName)?.dirty
  }

  toggleShowPassword(){
    this.showPassword = !this.showPassword
  }

  logIn(){

    this.loader = true;

    this.authSvc.logIn(this.loginForm.value)
    .pipe(catchError(error=>{
      if(error.error.message === "username/password errate" || error.error.message === "Utente non trovato"){
        this.swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Username o Password errate!"
        });
        this.loader = false;

      } else{
        this.swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Problemi di comunicazione con il server, controlla la tua conessione!"
        });
        this.loader = false;
      }
      throw error;
    })

    ).subscribe(data =>{
      if(data.user.ruolo === 'USER'){
        this.router.navigate(['../../welcomeUser']);
      }else if(data.user.ruolo === 'ADMIN'){
        this.router.navigate(['../../welcomeAdmin']);
      }
      this.loader = false
    })

  }

}
