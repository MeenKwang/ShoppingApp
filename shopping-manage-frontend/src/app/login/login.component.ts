import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { AuthRequest } from '../model/auth-request.model';
import { UserService } from '../service/user/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  auth : AuthRequest = new AuthRequest();
  constructor(
    private cookieService: CookieService,
    private router: Router,
    private userService: UserService) { }

  ngOnInit(): void {
    if(this.cookieService.check("ShopManagementToken") === true) {
      this.router.navigate(["/home"]);
    }
  }

  onLogin() {
    this.userService.doLogin(this.auth).subscribe(
      (response: any) => {
        this.cookieService.set("ShopManagementToken", response.accessToken);
        this.router.navigate(["/home"]);
      }
    );
  }

}
