import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { faPenToSquare, faTrash, faSearch, faUserPlus, faFileExcel, faFileCsv, faFilePdf, faArrowUp, faArrowDown, faEdit, faPlusSquare } from '@fortawesome/free-solid-svg-icons';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmModalComponent } from 'src/app/modal/confirm-modal/confirm-modal.component';
import { NotifyModalComponent } from 'src/app/modal/notify-modal/notify-modal.component';
import { Brand } from 'src/app/model/brand.model';
import { BrandService } from 'src/app/service/brand/brand.service';

@Component({
  selector: 'app-brand-list',
  templateUrl: './brand-list.component.html',
  styleUrls: ['./brand-list.component.css']
})
export class BrandListComponent implements OnInit {

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
  pageSize : number = 10;
  totalPages : number = 0;
  totalElements : number = 0;

  brandsList : Array<Brand> = [];
  message: any;

  constructor(
    private brandService: BrandService,
    private router: Router,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.getFirstPage();
  }

  getFirstPage() {
    this.brandService.listFirstPage().subscribe(
      (response : any) => {
        this.brandsList = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
      }
    )
  }

  getPageList() {
    this.brandService.listBrandsByPage(this.pageNum, this.nameSearch, this.sortField, this.sortDir, this.pageSize).subscribe(
      (response : any) => {
        this.brandsList = response.content;
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

  createBrand() {
    this.router.navigate(["brands/new"]);
  }

  edit(id : number | undefined) {
    this.router.navigate(["brands/edit", id]);
  }

  delete(id: number | any) {
    const modalRef = this.modalService.open(ConfirmModalComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.object = "brand";
    modalRef.componentInstance.emitService.subscribe((emmitedValue : any) => {
      this.message = emmitedValue;
      if(this.message === "Successfully!") {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This brand has been delete successfully!";
        this.getPageList();
        this.sortField = "name";
        this.targetSortField = "name";
        this.sortDir = "asc";
        this.nameSearch = "";
      } 
      
      if(this.message === "Cannot delete!") {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This brand cannot be deleted!";
      } 
      
      if(this.message === "Error response from server!") {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This brand cannot be deleted!";
      }
    });
  }

}
