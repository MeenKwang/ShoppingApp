import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpHeaders
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthService } from '../service/auth/auth.service';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private cookieService: CookieService,
    private authService: AuthService,
    private router: Router
  ) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(req.headers.get("No-Auth") === "True") {
        return next.handle(req.clone());
    }

    const token = "Bearer " + this.authService.getOriginalToken();
    const headers = new HttpHeaders().set('Authorization', token);
    const AuthRequest = req.clone({headers: headers});

    return next.handle(AuthRequest).pipe(
        catchError(
            (err: HttpErrorResponse) => {
                console.log("Error status" + err.status);
                console.log("Authorization header" + AuthRequest.headers.get("Authorization"));
                if(err.status === 401) {
                  this.cookieService.deleteAll();
                    this.router.navigate(['/login']);
                } else if(err.status === 403){
                    this.router.navigate(['/forbidden'])
                } else if(err.status === 404) {
                    this.router.navigate(['/**'])
                } else if(err.status === 405) {
                  this.router.navigate(['/home']);
                } else if(err.status === 500) {
                  this.router.navigate(['/home']);
                } else {

                }
                return throwError(() => err);
            }
        )
    )
  }
}
