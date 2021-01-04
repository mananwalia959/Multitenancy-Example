import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private readonly auth: AuthService, private readonly router: Router){}


  canActivate( next: ActivatedRouteSnapshot,): boolean {


    if(this.auth.authToken)
      return true;
    else{
      this.router.navigate(['login']);
      return false;
    }
  }
  
}
