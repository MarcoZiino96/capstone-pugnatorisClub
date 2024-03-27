import { Component, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IResponseData } from '../../../Models/interfaceUtente/i-response-data';
import { AuthService } from '../../../Services/auth.service';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IEdit } from '../../../Models/interfaceUtente/i-edit';
import { catchError } from 'rxjs';
import { IPassword } from '../../../Models/interfaceUtente/i-password';



@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.scss'
})

export class EditComponent {

  fileUrl!: string;
  match: boolean = false;
  msg!: IEdit;
  errorMsg!: IEdit;
  msgPassword!: IPassword
  errorMsgPassword!: IPassword
  registered: boolean = false;
  file!: File
  accessData!: number | undefined;
  showPass: boolean = false;
  showEdit: boolean = true;
  showOldPassword: boolean = false;
  showNewPassword: boolean = false;
  showConfirmPassword: boolean = false

  iUser: IResponseData = {
    dateResponse: new Date(),
    message: '',
    response: {
      id: 0,
      password: '',
      ruolo: '',
      nome: '',
      cognome: '',
      email: '',
      fotoProfilo: null,
      dataNascita: null,
    },
  }

  constructor(private route: ActivatedRoute,
    private authSvc: AuthService,
    private fb: FormBuilder,
    private router: Router,
    @Inject('Swal') private swal: any) { }

  editForm: FormGroup = this.fb.group({
    nome: this.fb.control(null, [Validators.required, Validators.minLength(3), Validators.maxLength(10), Validators.pattern(/^[a-zA-Z\s]*$/)]),
    cognome: this.fb.control(null, [Validators.required, Validators.minLength(3), Validators.maxLength(10), Validators.pattern(/^[a-zA-Z\s]*$/)]),
    email: this.fb.control(null, [Validators.required, Validators.pattern(/^[\w-.]+@([\w-]+.)+[\w-]{2,4}$/)]),
    dataNascita: this.fb.control(null, [Validators.required, Validators.pattern(/^\d{4}-\d{2}-\d{2}$/)]),
  });

  editFotoForm: FormGroup = this.fb.group({
    fotoProfilo: this.fb.control(this.iUser.response.dataNascita, [Validators.required])
  })

  editPasswordForm: FormGroup = this.fb.group({
    oldPassword: this.fb.control(null, [Validators.required, Validators.minLength(8), Validators.maxLength(16), Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>])(?=.*[^\s]).{8,}$/)]),
    newPassword: this.fb.control(null, [Validators.required, Validators.minLength(8), Validators.maxLength(16), Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>])(?=.*[^\s]).{8,}$/)]),
    confirmPassword: this.fb.control(null, [Validators.required])
  })


  ngOnInit() {


    this.authSvc.authSubject.subscribe((data) => {
      this.accessData = data?.user.id
    })

    this.route.params.subscribe((params: any) => {
      if (this.accessData != params.id) {
        this.swal.fire({
          icon: "error",
          title: "Non fare il furbo...",
          text: "Nessun utente trovato"
        })
        this.router.navigate(['../../welcomeUser']);
      } else {
        this.authSvc.getById(params.id).subscribe((res => {
          if (!res) {
            this.swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Nessun utente trovato"
            });
          }
          this.iUser = res

          this.editForm.patchValue({
            nome: this.iUser.response.nome,
            cognome: this.iUser.response.cognome,
            dataNascita: this.iUser.response.dataNascita,
            email: this.iUser.response.email,
          });
        }))
      }
    })
  }

  ngDoCheck() {
    this.errorMsg = {
      nome: this.invalidMessages('nome'),
      cognome: this.invalidMessages('cognome'),
      email: this.invalidMessages('email'),
      dataNascita: this.invalidMessages("dataNascita"),
    }

    this.errorMsgPassword = {
      oldPassword: this.invalidMessagesPass("oldPassword"),
      newPassword: this.invalidMessagesPass("newPassword"),
      confirmPassword: (this.editPasswordForm.controls['confirmPassword'].value !== this.editPasswordForm.controls['newPassword'].value) && (this.editPasswordForm.controls['confirmPassword'].dirty) ? 'Mancata corrispondenza' : this.invalidMessages('confirmPassword')
    }

    this.msg = {
      email: '',
      nome: '',
      cognome: '',
      dataNascita: ''
    }

    this.msgPassword = {
      oldPassword: '',
      newPassword: '',
      confirmPassword: ''
    }

    if (this.editPasswordForm.controls["newPassword"].value === this.editPasswordForm.controls["confirmPassword"].value && this.editPasswordForm.controls["confirmPassword"].dirty) {
      this.match = true
    } else {
      this.match = false
    }

    if (this.errorMsg.nome) {
      this.msg.nome = this.errorMsg.nome
    } else {
      this.msg.nome = 'Campo compilato correttamente'
    }

    if (this.errorMsg.cognome) {
      this.msg.cognome = this.errorMsg.cognome
    } else {
      this.msg.cognome = 'Campo compilato correttamente'
    }

    if (this.errorMsg.email) {
      this.msg.email = this.errorMsg.email
    } else {
      this.msg.email = 'Campo compilato correttamente'
    }

    if (this.errorMsg.dataNascita) {
      this.msg.dataNascita = this.errorMsg.dataNascita
    } else {
      this.msg.dataNascita = 'Campo compilato correttamente'
    }

    if (this.errorMsgPassword.oldPassword) {
      this.msgPassword.oldPassword = this.errorMsgPassword.oldPassword
    } else {
      this.msgPassword.oldPassword = 'Campo compilato correttamente'
    }

    if (this.errorMsgPassword.newPassword) {
      this.msgPassword.newPassword = this.errorMsgPassword.newPassword
    } else {
      this.msgPassword.newPassword = 'Campo compilato correttamente'
    }

    if (this.errorMsgPassword.confirmPassword) {
      this.msgPassword.confirmPassword = this.errorMsgPassword.confirmPassword
    } else {
      this.msgPassword.confirmPassword = 'Le due password combaciano'
    }
  }

  invalidMessages(fieldName: string): string {
    const field: AbstractControl | null = this.editForm.get(fieldName);
    let errorMsg: string = "";

    if (field)
      if (field.errors) {

        if (field.errors['required'])
          errorMsg = 'Campo vuoto'

        if (field.errors['pattern'] && fieldName === 'email')
          errorMsg = 'Formato email errato'

        if (field.errors['pattern'] && fieldName === 'dataNascita')
          errorMsg = 'Formato data errato'

        if (field.errors['minlength'] && (fieldName === 'nome' || fieldName === 'cognome'))
          errorMsg = 'Lunghezza minima: 3 caratteri'

        if (field.errors['maxlength'] && (fieldName === 'nome' || fieldName === 'cognome'))
          errorMsg = 'Lunghezza massima: 10 caratteri'

        if (field.errors['pattern'] && (fieldName === 'nome' || fieldName === 'cognome'))
          errorMsg = 'Sono ammesse solo lettere dell\'alfabeto'
      }
    return errorMsg;
  }

  invalidMessagesPass(fieldName: string): string {
    const field: AbstractControl | null = this.editPasswordForm.get(fieldName);
    let errorMsgPassword: string = "";

    if (field)
      if (field.errors) {

        if (field.errors['required'])
          errorMsgPassword = 'Campo vuoto'

        if (field.errors['pattern'] && (fieldName === 'newPassword' || fieldName === 'oldPassword'))
          errorMsgPassword = "Password deve contenere: 1 lettera maiuscola, 1 lettera minuscola, 1 numero e 1 carattere speciale"

        if (field.errors['minlength'] && (fieldName === 'newPassword' || fieldName === 'oldPassword'))
          errorMsgPassword = 'Lunghezza minima password: 8 caratteri'

        if (field.errors['maxlength'] && (fieldName === 'newPassword' || fieldName === 'oldPassword'))
          errorMsgPassword = 'Lunghezza massima password: 16 caratteri'

        if (field.errors['minlength'] && fieldName === 'confirmPassword')
          errorMsgPassword = 'Lunghezza minima password: 8 caratteri'
      }
    return errorMsgPassword;
  }

  isValid(inputName: string) {
    return this.editForm.get(inputName)?.valid && this.editForm.get(inputName)?.dirty
  }

  isInvalid(inputName: string) {
    return !this.editForm.get(inputName)?.valid && this.editForm.get(inputName)?.dirty
  }

  isValidFormPass(inputName: string) {
    return this.editPasswordForm.get(inputName)?.valid && this.editPasswordForm.get(inputName)?.dirty
  }

  isInvalidFormPass(inputName: string) {
    return !this.editPasswordForm.get(inputName)?.valid && this.editPasswordForm.get(inputName)?.dirty
  }

  toggleShowOldPassword() {
    this.showOldPassword = !this.showOldPassword
  }

  toggleShowNewPassword() {
    this.showNewPassword = !this.showNewPassword
  }

  toggleShowConfirmPassword() {
    this.showConfirmPassword = !this.showConfirmPassword
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];

    if (this.file) {
      const READER = new FileReader();
      READER.readAsDataURL(this.file);
      READER.onload = () => {
        this.fileUrl = READER.result as string;
      };
    }
  }

  edit() {

    const editData: any = this.editForm.value

    editData.dataNascita = new Date(editData.dataNascita)

    this.route.params.subscribe((params: any) => {
      this.authSvc.getById(params.id).subscribe((res => {

        this.iUser = res

        this.authSvc.edit(this.iUser.response.id, editData)
          ?.pipe(catchError(error => {
            if (error.error.message) {
              this.swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Compila tutti i campi correttamente"
              });
            }
            throw error;
          }))
          .subscribe((response => {
            if (!response) {
              this.swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Qualcosa è andato storto, la modifica non è andata a buon fine!"
              })
            } else {
              this.swal.fire({
                title: "Good job!",
                text: "Modifiche apportate con  successo!",
                icon: "success"
              })
              this.router.navigate(['../../welcomeUser']);
            }
          }))
      }))
    })
  }

  editFoto() {

    this.route.params.subscribe((params: any) => {
      this.authSvc.getById(params.id).subscribe(
        (res) => {
          this.iUser = res;

          this.authSvc.uploadFile(this.iUser.response.id, this.file)
            .pipe(catchError(error => {
              if (error.error.message) {
                this.swal.fire({
                  icon: "error",
                  title: "Oops...",
                  text: "Scegli un file prima di caricarlo!!"
                });
              } else {
                this.swal.fire({
                  icon: "error",
                  title: "Oops...",
                  text: "Problemi di comunicazione con il server, controlla la tua conessione!"
                });
              }
              throw error;
            }))
            .subscribe((res) => {
              if (res) {
                this.swal.fire({
                  title: "Good job!",
                  text: "La tua foto è stata caricata con successo!",
                  icon: "success"
                })
              } else if (!res) {
                this.swal.fire({
                  icon: "error",
                  title: "Oops...",
                  text: "Qualcosa è andato storto, la foto non è stata caricata!"
                })
              }
            }
            )
        })
    })
  }


  editPassword() {
    const passwordFormData: any = this.editPasswordForm.value;
    delete passwordFormData.confirmPassword

    this.route.params.subscribe((params: any) => {
      this.authSvc.getById(params.id).subscribe(
        (res) => {

          this.iUser = res;

          this.authSvc.changePassword(this.iUser.response.id, passwordFormData)
            .pipe(catchError(error => {
              if (error.error.message === 'Password sbagliata') {
                this.swal.fire({
                  icon: "error",
                  title: "Oops...",
                  text: "La tua vecchia password è errata"
                });

                this.editPasswordForm.reset();
              } if (error.error.message === "[campo obbligatorio, campo obbligatorio]" || error.error.message === "[campo obbligatorio]") {
                this.swal.fire({
                  icon: "error",
                  title: "Oops...",
                  text: "Compila correttamente tutti i campi"
                });

                this.editPasswordForm.reset();
              }
              else if (error.error.message === 'Password vecchia uguale a quella nuova') {
                this.swal.fire({
                  icon: "error",
                  title: "Oops...",
                  text: "La vecchia password è uguale alla nuova password"
                });
                this.editPasswordForm.reset();
              }
              throw error;
            }))
            .subscribe((res) => {
              if (res) {
                this.swal.fire({
                  title: "Good job!",
                  text: "Password cambiata con successo!",
                  icon: "success"
                })
                this.router.navigate(['/welcomeUser'])

              } else {
                this.swal.fire({
                  icon: "error",
                  title: "Oops...",
                  text: "Qualcosa è andato storto"
                })
              }
            }
            )
        }
      )
    })
  }
}
