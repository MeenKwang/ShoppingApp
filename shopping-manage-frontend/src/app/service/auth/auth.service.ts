import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import jwt_decode from "jwt-decode";
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private cookieService: CookieService
  ) { }

  public getOriginalToken() {
    return this.cookieService.get("ShopManagementToken");
    
  }

  public getToken() : string | any {
    return jwt_decode(this.cookieService.get("ShopManagementToken"));
  }

  public getRoles() {
    let roles : string | any = this.getToken().roles;
    roles = roles.replace("[", "");
    roles = roles.replace("]", "");
    roles = roles.replaceAll(" ", "");
    let rolesArray: Array<string> = roles.split(",");
    return rolesArray;
  }

  public setToken(token : string) {
    this.cookieService.set("ShopManagementToken", token);
  }

  public roleMatch(allowedRoles : any) : boolean {
    let isMatch = false;
    const userRoles = this.getRoles();

    if(userRoles != null && userRoles) {
      for(let i = 0; i < userRoles.length; i++) {
        for(let j = 0; j < allowedRoles.length; j++) {
          if(userRoles[i] === allowedRoles[j]) {
            isMatch = true;
            return isMatch;
          }
        }
      }
    }
    return isMatch;
  }

}

