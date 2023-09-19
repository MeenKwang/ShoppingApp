import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { faPenToSquare, faTrash, faSearch, faUserPlus, faFileExcel, faFileCsv, faFilePdf, faArrowUp, faArrowDown, faEdit, faPlusSquare } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmModalComponent } from 'src/app/modal/confirm-modal/confirm-modal.component';
import { NotifyModalComponent } from 'src/app/modal/notify-modal/notify-modal.component';
import { Category } from 'src/app/model/category.model';
import { CategoryService } from 'src/app/service/category/category.service';
@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {
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
  sortField : string = "name";
  targetSortField: string ="name";
  sortDir : string = "asc";
  pageSize : number = 5;
  totalPages : number = 0;
  totalElements : number = 0;

  categoriesList : Array<Category> = [];
  message: any;

  constructor(
    private categoryService : CategoryService,
    private router: Router,
    private route: ActivatedRoute,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.getFirstPage();
  }

  getFirstPage() {
    this.categoryService.listFirstPage().subscribe(
      (response : any) => {
        this.categoriesList = response.categoriesList;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
      }
    )
  }

  getPageList() {
    this.categoryService.listCategoryByPage(this.pageNum, this.nameSearch, this.sortField, this.sortDir, this.pageSize).subscribe(
      (response : any) => {
        this.categoriesList = response.categoriesList;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
      }
    )
  }

  sortPage(fieldName : string) {
    if(this.sortField !== fieldName) this.sortDir = "";
    this.sortField = fieldName;
    this.sortDir = this.getSortDir();
    this.getPageList();
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

  createCategory() {
    this.router.navigate(["categories/new"]);
  }

  edit(id : number | undefined) {
    this.router.navigate(["categories/edit", id]);
  }

  delete(id: number | any) {
    const modalRef = this.modalService.open(ConfirmModalComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.object = "category";
    modalRef.componentInstance.emitService.subscribe((emmitedValue : any) => {
      this.message = emmitedValue;
      if(this.message === "Successfully!") {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This category has been delete successfully!";
        this.getPageList();
        this.sortField = "name";
        this.targetSortField = "name";
        this.sortDir = "asc";
        this.nameSearch = "";
      } 
      
      if(this.message === "Cannot delete!") {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This category cannot be deleted!";
      } 
      
      if(this.message === "Error response from server!") {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This category cannot be deleted!";
      }
    });
  }
}
