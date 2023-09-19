import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Role } from 'src/app/model/role.model';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  private base_url = "http://localhost:8080/ShoppingAdmin/";

  constructor(private httpClient: HttpClient) { }

  public getAllRoles() : Observable<Role[]> | any {
    return this.httpClient.get(this.base_url + 'api/roles/getAllRolesForUserForm');
  }
}
