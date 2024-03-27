import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable, map } from 'rxjs';
import { AuthService } from '../Services/auth.service';
import { IAuthData } from '../Models/interfaceUtente/i-auth-data';

@Injectable({
  providedIn: 'root'
})
export class WelcomeGuard{

  constructor(private authSvc:AuthService, private router: Router){}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.authSvc.isLoggedIn$.pipe(map((res:boolean)=>{
    if(!res) this.router.navigate(['/auth/login'])
    return res
    }));
  }
  canActivateChild(
    childRoute: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      return true;
  }

}
