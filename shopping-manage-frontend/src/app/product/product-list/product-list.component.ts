
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { faCheckCircle, faTrash, faSearch, faUserPlus, faFileExcel, faFileCsv, faFilePdf, faArrowUp, faArrowDown, faEdit, faPlusSquare } from '@fortawesome/free-solid-svg-icons';
import { Product } from 'src/app/model/product.model';

import { ProductService } from 'src/app/service/product/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  faCheckCircle = faCheckCircle;
  faPlusSquare = faPlusSquare;
  faEdit = faEdit;
  faTrash = faTrash;
  faSearch = faSearch;
  faUserPlus = faUserPlus;
  faFileExcel = faFileExcel;
  faFileCsv = faFileCsv;
  faFilePdf = faFilePdf;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;

  pageNum : number = 1;
  nameSearch : string = '';
  sortField : string = "id";
  targetSortField: string ="id";
  sortDir : string = "asc";
  pageSize : number = 15;
  categoryId : number = 0;
  brandId : number = 0;
  totalPages : number = 0;
  totalElements : number = 0;

  productList : Array<Product> = [];

  constructor(
    private productService: ProductService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.listFirstPage();
  }

  listFirstPage() {
    this.productService.listFirstPage().subscribe({
      next : (response : any) => {
        this.productList = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
      }
    })
  }

  listByPage() {
    this.productService.listProductsByPage(this.pageNum, this.categoryId, this.brandId, this.nameSearch, this.sortField, this.sortDir, this.pageSize)
    .subscribe({
      next : (response : any) => {
      console.log(response);
      this.productList = response.content;
      this.totalPages = response.totalPages;
      this.totalElements = response.totalElements;
      }
    })
  }

    sortPage(fieldName : string) {
      if(this.sortField !== fieldName) this.sortDir = "";
      this.sortField = fieldName;
      this.sortDir = this.getSortDir();
      this.listByPage();
    }
  
    private getSortDir() : string {
      if(this.sortDir === "") return "asc";
      else if(this.sortDir === "desc") return "asc";
      else return "desc";
    }
  
    isTargetSortField(fieldName : string) {
      if(this.sortField === fieldName) return true;
      return false;
    }

    createProduct() {
      this.router.navigate(["/products/new"]);
    }

    edit(id : number | any) {
      this.router.navigate(['products/edit', id]);
    }

    delete(id : number | any) {
      
    }
}
