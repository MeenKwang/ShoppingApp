import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { Product } from 'src/app/model/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private base_url = "http://localhost:8080/ShoppingAdmin/";

  constructor(
    private httpClient : HttpClient
    
  ) { }

  public listFirstPage() {
    return this.httpClient.get(this.base_url + "api/products");
  }

  public listProductsByPage(pageNum : number | any, categoryId : number | any, brandId : number | any, nameSearch : string | any, sortField : string, sortDir : string,
    pageSize : number) {

    let params = new HttpParams();
    params = params.append('categoryId', categoryId);
    params = params.append('brandId', brandId);
    params = params.append('nameSearch', nameSearch);
    params = params.append('sortField', sortField);
    params = params.append('sortDir', sortDir);
    params = params.append('pageSize', pageSize);
    
    return this.httpClient.get(`${this.base_url}api/products/page/${pageNum}`, { params : params }).pipe();
  }

  public listBrandsUsedInForm() {
    return this.httpClient.get(this.base_url + "api/brands/list_brands_used_in_form").pipe();
  }

  public listCategoriesUsedInForm(id : number | any) {
    return this.httpClient.get(`${this.base_url}api/brands/${id}/categories`).pipe();
  }

  public saveProduct(productForm : Product, mainImageFile : File, extraImages : File[]) {
    console.log(mainImageFile);
    console.log(extraImages);
    let formData =  new FormData();
    formData.append("productForm", JSON.stringify(productForm));
    formData.append("mainImageFile", mainImageFile);
    for(let extraImage of extraImages) {
      formData.append("extraImages", extraImage);
    }
    return this.httpClient.post(this.base_url + "api/products/save", formData);
  }

  // public saveCategory(categoryForm : CategoryForm, image : File) {
  //   let form = new FormData();
  //   form.append("categoryForm", JSON.stringify(categoryForm));
  //   form.append("image", image);
  //   return this.httpClient.post(this.base_url + "api/categories/save", form);
  // }

  // public deleteCategory(id : number) {
  //   return this.httpClient.delete(`${this.base_url}api/categories/delete/${id}`).pipe();
  // }

  public getProductById(id: number | any) {
    return this.httpClient.get(`${this.base_url}api/products/get/${id}`).pipe();
  }
}
