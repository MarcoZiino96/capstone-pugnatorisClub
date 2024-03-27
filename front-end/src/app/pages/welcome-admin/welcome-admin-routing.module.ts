import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeAdminComponent } from './welcome-admin.component';
import { CreateComponent } from './create/create.component';
import { DetailsComponent } from './details/details.component';

const routes: Routes = [
  {
    path: '', component: WelcomeAdminComponent
  },
  {
    path: 'create/:id', component: CreateComponent, title:'Create - PugnatorisClub'
  },
  {
    path: 'details/:id', component: DetailsComponent, title:'Details - PugnatorisClub'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WelcomeAdminRoutingModule { }
