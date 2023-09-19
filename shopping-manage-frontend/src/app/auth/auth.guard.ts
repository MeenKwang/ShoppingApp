import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from '../service/auth/auth.service';
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private cookieService: CookieService,
    private router: Router,
    private authService: AuthService
  ){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

      if(this.cookieService.check("ShopManagementToken") === true) {
        const roles = route.data["roles"] as Array<string | null>;

        if(roles) {
          const match = this.authService.roleMatch(roles);

          if(match) {
            return true;
          } else {
            this.router.navigate(['/forbidden']);
            return false;
          }
        }
      }
      
      this.router.navigate(['/login']);
      return false;

  }
  
}
