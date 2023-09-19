import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CategoryForm } from 'src/app/model/category-form-model';
import { Category } from 'src/app/model/category.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private base_url = "http://localhost:8080/ShoppingAdmin/";

  constructor(
    private httpClient : HttpClient
  ) {}

    public listFirstPage() {
      return this.httpClient.get(this.base_url + "api/categories");
    }

    public listCategoryByPage(pageNum : number | any, nameSearch : string | any, sortField : string, sortDir : string,
      pageSize : number) {

      let params = new HttpParams();
      params = params.append('nameSearch', nameSearch);
      params = params.append('sortField', sortField);
      params = params.append('sortDir', sortDir);
      params = params.append('pageSize', pageSize);
      
      return this.httpClient.get(`${this.base_url}api/categories/page/${pageNum}`, { params : params }).pipe();
    }

    public listCategoriesUsedInForm() {
      return this.httpClient.get(this.base_url + "api/categories/list_categories_used_in_form").pipe();
    }

    public saveCategory(categoryForm : CategoryForm, image : File) {
      let form = new FormData();
      form.append("categoryForm", JSON.stringify(categoryForm));
      form.append("image", image);
      return this.httpClient.post(this.base_url + "api/categories/save", form);
    }

    public deleteCategory(id : number) {
      return this.httpClient.delete(`${this.base_url}api/categories/delete/${id}`).pipe();
    }

    public getCategoryById(id: number | any) {
      return this.httpClient.get(`${this.base_url}api/categories/${id}`).pipe()
    }

}
