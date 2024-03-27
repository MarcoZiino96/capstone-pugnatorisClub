import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeGuard } from './guard/welcome.guard';
import { WelcomeAdminGuard } from './guard/welcome-admin.guard';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./pages/homepage/homepage.module').then(m => m.HomepageModule)
  },
{
    path: 'auth',
  loadChildren: () => import('./pages/auth/auth.module').then(m => m.AuthModule)
},
  {
    path:'homepage',
  loadChildren: () => import('./pages/homepage/homepage.module').then(m => m.HomepageModule)
},
{
  path: 'welcomeUser',
  loadChildren: () => import('./pages/welcome-user/welcome-user.module').then(m => m.WelcomeUserModule),
  canActivate: [WelcomeGuard]
},
{
  path: 'welcomeAdmin', loadChildren: () => import('./pages/welcome-admin/welcome-admin.module').then(m => m.WelcomeAdminModule),
  canActivate:[WelcomeAdminGuard]
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
