import { Component, Directive, EventEmitter, Input, OnDestroy, OnInit, Output, QueryList, ViewChildren } from '@angular/core';
import { User } from 'src/app/model/user.model';
import { UserService } from 'src/app/service/user/user.service';
import { faPenToSquare, faTrash, faSearch, faUserPlus, faFileExcel, faFileCsv, faFilePdf, faArrowUp, faArrowDown, faEdit } from '@fortawesome/free-solid-svg-icons';
import { FormControl } from '@angular/forms';
import { map, startWith } from 'rxjs';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ConfirmModalComponent } from 'src/app/modal/confirm-modal/confirm-modal.component';
import { NotifyModalComponent } from 'src/app/modal/notify-modal/notify-modal.component';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit {
  message : string = '';

  faEdit = faEdit;
  faTrash = faTrash;
  faSearch = faSearch;
  faUserPlus = faUserPlus;
  faFileExcel = faFileExcel;
  faFileCsv = faFileCsv;
  faFilePdf = faFilePdf;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;

  userList : Array<User> = [];
  totalPages : number = 0;
  currentPage : number = 1;
  pageSize : number = 15;
  totalPageList? : Array<number>;
  totalElements : number = 0;

  firstNameSearch: string = "";
  lastNameSearch: string = "";
  emailSearch: string = "";
  sortField: string = "firstName";
  targetSortField: string ="firstName";
  sortDir: string = "asc";

  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.getUserList();
    this.route.queryParams
    .pipe(
      map(para => para['data'])
    )
    .subscribe(v => {
      if(v !== undefined) this.message = v;
    });
  }

  public getUserList() {
    return this.userService.listAllUser().subscribe(
      (response : any) => {
        this.userList = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
      }
    );
  }

  public getPageList() {
    return this.userService.listUsersByPage(this.currentPage, this.firstNameSearch, this.lastNameSearch, this.emailSearch, 
      this.sortField, this.sortDir, this.pageSize).subscribe(
      (response : any) => {
        this.userList = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
      }
    );
  }

  private getSortDir() : string {
    if(this.sortDir === "") return "asc";
    else if(this.sortDir === "desc") return "asc";
    else return "desc";
  }

  public isTargetSortField(fieldName: string) : boolean {
    if(this.sortField === fieldName) return true;
    return false;
  }

  public sortPage(fieldName: string) {
    if(this.sortField !== fieldName) this.sortDir = "";
    this.sortField = fieldName;
    this.sortDir = this.getSortDir();
    this.getPageList();
  }

  createUser() {
    this.router.navigate(["users/new"]);
  }

  edit(id: number | any) {
    this.router.navigate(['users/edit', id]);
  }

  delete(id: number | any) {
    const modalRef = this.modalService.open(ConfirmModalComponent);
    modalRef.componentInstance.id = id;
    modalRef.componentInstance.object = "user";
    modalRef.componentInstance.emitService.subscribe((emmitedValue : any) => {
      this.message = emmitedValue;
      if(this.message === "Successfully!") {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This user has been deleted successfully!";
        this.getUserList();
        this.sortField = "firstName";
        this.targetSortField = "firstName";
        this.sortDir = "asc";
        this.firstNameSearch = "";
        this.lastNameSearch = "";
        this.emailSearch = "";
      } 
      
      if(this.message === "Cannot delete!") {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This user cannot be deleted!";
      } 
      
      if(this.message === "Error response from server!") {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This user cannot be deleted!";
      }
    });
  }

  exportToExcel() {
    console.log("Ok");
    this.userService.exportExcel();
  }

}
