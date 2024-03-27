import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage.component';
import { ChiSiamoComponent } from './chi-siamo/chi-siamo.component';
import { MaestriComponent } from './maestri/maestri.component';

const routes: Routes = [
  { path: '', component: HomepageComponent },
  { path: 'chi-siamo', component: ChiSiamoComponent, title: 'Chi Siamo - PugnatorisClub'},
  { path: 'maestri', component: MaestriComponent, title: 'Maestri - PugnatorisClub' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomepageRoutingModule { }
