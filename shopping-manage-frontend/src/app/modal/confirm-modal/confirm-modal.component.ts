import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { BrandService } from 'src/app/service/brand/brand.service';
import { CategoryService } from 'src/app/service/category/category.service';
import { UserService } from 'src/app/service/user/user.service';

@Component({
  selector: 'app-confirm-modal',
  templateUrl: './confirm-modal.component.html',
  styleUrls: ['./confirm-modal.component.css']
})
export class ConfirmModalComponent implements OnInit {
  @Input() id: any;
  @Input() object: any;
  @Output() emitService = new EventEmitter();
  constructor(
    public activeModal: NgbActiveModal,
    private userService: UserService,
    private categoryService: CategoryService,
    private brandService: BrandService
  ) { }

  ngOnInit(): void {
  }

  public delete() {
    if(this.object === "user") {
      this.userService.deleteUser(this.id).subscribe({
        next: (response) => {
          if(response === true) {
            this.emitService.next("Successfully!");
          } else {
            this.emitService.next("Cannot delete!");
          }
        },
        error: (error) => {
          this.emitService.next("Error response from server!");
        }
      })
    } else if(this.object === "category") {
      this.categoryService.deleteCategory(this.id).subscribe({
        next: (response) => {
          if(response === true) {
            this.emitService.next("Successfully!");
          } else {
            this.emitService.next("Cannot delete!");
          }
        },
        error: (error) => {
          this.emitService.next("Error response from server!");
        }
      })
    } else if(this.object === "brand") {
      this.brandService.delete(this.id).subscribe({
        next: (response) => {
          if(response === true) {
            this.emitService.next("Successfully!");
          } else {
            this.emitService.next("Cannot delete!");
          }
        },
        error: (error) => {
          this.emitService.next("Error response from server!");
        }
      })
    }

    this.activeModal.close();
  }

}
