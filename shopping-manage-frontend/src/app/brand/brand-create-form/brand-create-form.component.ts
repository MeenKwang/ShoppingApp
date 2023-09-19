import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NotifyModalComponent } from 'src/app/modal/notify-modal/notify-modal.component';
import { BrandForm } from 'src/app/model/brand-form.model';
import { CategorySelect } from 'src/app/model/category-select.model';
import { BrandService } from 'src/app/service/brand/brand.service';
import { CategoryService } from 'src/app/service/category/category.service';

@Component({
  selector: 'app-brand-create-form',
  templateUrl: './brand-create-form.component.html',
  styleUrls: ['./brand-create-form.component.css']
})
export class BrandCreateFormComponent implements OnInit {

  categoriesSelect : Array<CategorySelect> | any;
  brandForm! : FormGroup;
  imageURL: string = '';
  submitted: boolean = false;
  brandFormModel: BrandForm = {};

  constructor(
    private brandService: BrandService,
    private categoryService: CategoryService,
    private fb: FormBuilder,
    private router: Router,
    private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.initBrandForm();

    this.getCategorySelect();
  }

  getCategorySelect() {
    this.categoryService.listCategoriesUsedInForm().subscribe(
      (response) => {
        this.categoriesSelect = response;
      }
    )
  }

  initBrandForm() {
    this.brandForm = this.fb.group({
      id: new FormControl(''),
      name : new FormControl('', Validators.required),
      logo : new FormControl(null, Validators.required),
      categories : new FormControl([], Validators.required),
    });
  }

  submit() {
    this.submitted = true;
    if(this.brandForm.invalid) {
      return;
    }
    this.bindValueToCategoryFormModel();
    console.log(this.brandFormModel);
    this.brandService.save(this.brandFormModel, this.brandForm.controls["logo"].value).subscribe({
      next: (response) => {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This brand has been create successfully!"
        this.router.navigate(["brands"]);
      },
      error: () => {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "Error when creating brand!"
      }
    })
  }

  private bindValueToCategoryFormModel() {
    this.brandFormModel.id = this.brandForm.controls["id"].value;
    this.brandFormModel.name = this.brandForm.controls["name"].value;
    this.brandFormModel.categories = [];
    let categoryIds : [] = this.brandForm.controls["categories"].value;
    categoryIds.forEach((id) => {
      this.brandFormModel.categories?.push(new CategorySelect(id));
    })
  } 

  public showPreview(event : any) {
    const file = event.target.files[0];
    if(file) {
      this.brandForm.patchValue({
        logo: file
      });
      this.brandForm.get('logo')?.updateValueAndValidity()
      // File Preview
      const reader = new FileReader();
      reader.onload = () => {
        this.imageURL = reader.result as string;
      }
      reader.readAsDataURL(file);
    } else {
      this.brandForm.patchValue({
        logo: null
      });
      this.imageURL = '';
    }
  }

  get f(): { [key: string]: AbstractControl } {
    return this.brandForm.controls;
  }

}
