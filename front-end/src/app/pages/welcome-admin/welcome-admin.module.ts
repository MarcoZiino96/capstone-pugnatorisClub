import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { WelcomeAdminRoutingModule } from './welcome-admin-routing.module';
import { WelcomeAdminComponent } from './welcome-admin.component';
import { CreateComponent } from './create/create.component';
import { ReactiveFormsModule } from '@angular/forms';
import { DetailsComponent } from './details/details.component';


@NgModule({
  declarations: [
    WelcomeAdminComponent,
    CreateComponent,
    DetailsComponent
  ],
  imports: [
    CommonModule,
    WelcomeAdminRoutingModule,
    ReactiveFormsModule
  ]
})
export class WelcomeAdminModule { }
