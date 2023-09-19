import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import jwt_decode from "jwt-decode";
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from '../service/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  username: string | null = "";

  constructor(
    private authService: AuthService,
    private cookieService: CookieService,
    private router: Router
  ) { 
    
  }

  ngOnInit(): void {
    this.username = this.getUsername();
  }

  public getUsername(): string | null {
    let token : any = jwt_decode(this.cookieService.get("ShopManagementToken"));
    let sub : Array<string> = token.sub.split(",");
    let name : string | null = sub[1];
    return name;
  }

  public getRoles () {
    let decodeAccessToken : any = jwt_decode(this.cookieService.get("ShopManagementToken"));
    let rolesList = decodeAccessToken.roles;
    rolesList = rolesList.replace("[", "");
    rolesList = rolesList.replace("]", "");
    rolesList = rolesList.replaceAll(" ", "");
    let rolesArray: Array<string> = rolesList.split(",");
    return rolesArray;
  }

  public logout() {
    this.cookieService.deleteAll();
    this.router.navigate(['login']);
    console.log(this.cookieService.check("ShopManagementToken"));
  }

  public haveRole(...allowedRoles: string[]): boolean {

    return this.authService.roleMatch(allowedRoles);
  }

  public navigate (url : string) {
    this.router.navigate([url]);
  }

}
