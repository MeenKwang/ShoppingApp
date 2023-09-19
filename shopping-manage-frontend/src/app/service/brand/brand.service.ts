import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BrandForm } from 'src/app/model/brand-form.model';

@Injectable({
  providedIn: 'root'
})
export class BrandService {

  private base_url = "http://localhost:8080/ShoppingAdmin/";

  constructor(
    private httpClient: HttpClient
  ) { }
  public listFirstPage() {
    return this.httpClient.get(this.base_url + "api/brands");
  }

  public listBrandsByPage(pageNum : number | any, nameSearch : string | any, sortField : string, sortDir : string,
    pageSize : number) {

    let params = new HttpParams();
    params = params.append('nameSearch', nameSearch);
    params = params.append('sortField', sortField);
    params = params.append('sortDir', sortDir);
    params = params.append('pageSize', pageSize);
    
    return this.httpClient.get(`${this.base_url}api/brands/page/${pageNum}`, { params : params }).pipe();
  }

  public save(form : BrandForm, logo : File) {
    let formData = new FormData();
    formData.append("brandForm", JSON.stringify(form));
    formData.append("logo", logo);

    return this.httpClient.post(this.base_url + "api/brands/save", formData).pipe();
  }

  public getBrandById(id : number) {
    return this.httpClient.get(`${this.base_url}api/brands/${id}`).pipe();
  }

  public delete(id : number) {
    return this.httpClient.delete(`${this.base_url}api/brands/delete/${id}`).pipe();
  }

}
