import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeUserComponent } from './welcome-user.component';
import { EditComponent } from './edit/edit.component';
import { PrenotazioneComponent } from './prenotazione/prenotazione.component';
import { UserDetailsComponent } from './user-details/user-details.component';

const routes: Routes = [
{
   path: '', component: WelcomeUserComponent
},
{
  path: 'edit/:id', component: EditComponent, title: 'Edit - PugnatorisClub'
},
{
  path: 'prenotazione/:idUtente/:idCorso', component: PrenotazioneComponent, title: 'Prenotazione - PugnatorisClub'
},
{
  path: 'user-details/:id', component: UserDetailsComponent, title: 'UserDetails - PugnatorisClub'
},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WelcomeUserRoutingModule { }
