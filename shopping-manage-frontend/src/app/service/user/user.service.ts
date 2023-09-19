import { DatePipe } from '@angular/common';
import { HttpClient, HttpHeaders, HttpParams, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { delay, Observable, of } from 'rxjs';
import { AuthRequest } from 'src/app/model/auth-request.model';
import { UserForm } from 'src/app/model/user-form.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private base_url = "http://localhost:8080/ShoppingAdmin/";
  
  requestHeader = new HttpHeaders(
    { "No-Auth": "True" }
  );

  constructor(
    private httpClient: HttpClient,
    private datepipe: DatePipe
  ) { }

  public doLogin(auth: AuthRequest) {
    return this.httpClient.post(this.base_url + "api/auth/login", auth, { headers: this.requestHeader })
  }

  public listAllUser() {
    return this.httpClient.get(this.base_url + "api/users");
  }

  public listUsersByPage(pageNum : number | any, firstNameSearch : string, lastNameSearch : string, 
      emailSearch : string, sortField : string, sortDir : string, pageSize : number) {
    let params = new HttpParams();
    params = params.append('firstNameSearch', firstNameSearch);
    params = params.append('lastNameSearch', lastNameSearch);
    params = params.append('emailSearch', emailSearch);
    params = params.append('sortField', sortField);
    params = params.append('sortDir', sortDir);
    params = params.append('pageSize', pageSize);
    return this.httpClient.get(`${this.base_url}api/users/page/${pageNum}`, { params : params});
  }

  public create(userFormModel: UserForm, image : any) {
    let formData = new FormData();
    formData.append("photo", image);
    formData.append("user", JSON.stringify(userFormModel));
    return this.httpClient.post(this.base_url + "api/users/save", formData);
  }
  
  public checkEmailUnique(id: any, email: string) {
    let params = new HttpParams();
    params = params.append("id", id);
    params = params.append("email", email);
    return this.httpClient.get(this.base_url + "api/users/check_email_unique", { params : params });
  }

  public deleteUser(id : number | any) {
    return this.httpClient.get(`${this.base_url}api/users/delete/${id}`);
  }

  public getUserById(id : number | any) : Observable<UserForm> | any {
    return this.httpClient.get(`${this.base_url}api/users/edit/${id}`);
  }

  public exportExcel() {
    this.httpClient.get(this.base_url + "api/users/export/excel", {responseType: "blob"})
    .subscribe({
      next: (blob : Blob) => {
        const downloadLink = document.createElement('a');
        downloadLink.href = URL.createObjectURL(blob);
        let currentDateTime =this.datepipe.transform((new Date), 'MM/dd/yyyy h:mm:ss');
        downloadLink.download = "users-list" + currentDateTime;
        downloadLink.click();
        URL.revokeObjectURL(downloadLink.href);
      },
      error: (error) => {
        console.error('Failed to download file:', error);
      }
    });
  }

}
