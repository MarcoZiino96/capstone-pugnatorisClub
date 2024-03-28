import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomepageRoutingModule } from './homepage-routing.module';
import { HomepageComponent } from './homepage.component';
import { ChiSiamoComponent } from './chi-siamo/chi-siamo.component';
import { MaestriComponent } from './maestri/maestri.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    HomepageComponent,
    ChiSiamoComponent,
    MaestriComponent,

  ],
  imports: [
    CommonModule,
    HomepageRoutingModule,
    NgbModule
  ]
})
export class HomepageModule { }
