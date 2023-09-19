import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NotifyModalComponent } from 'src/app/modal/notify-modal/notify-modal.component';
import { CategoryForm } from 'src/app/model/category-form-model';
import { CategorySelect } from 'src/app/model/category-select.model';
import { CategoryService } from 'src/app/service/category/category.service';

@Component({
  selector: 'app-category-create-form',
  templateUrl: './category-create-form.component.html',
  styleUrls: ['./category-create-form.component.css']
})
export class CategoryCreateFormComponent implements OnInit {

  categoriesSelect : Array<CategorySelect> | any;
  categoryForm! : FormGroup;
  imageURL: string = '';
  submitted: boolean = false;
  categoryFormModel: CategoryForm = {};

  constructor(
    private categoryService: CategoryService,
    private fb: FormBuilder,
    private router: Router,
    private modalService: NgbModal,
  ) { }

  ngOnInit(): void {

    this.initCategoryForm();

    this.getCategorySelect();

  }

  getCategorySelect() {
    this.categoryService.listCategoriesUsedInForm().subscribe(
      (response) => {
        this.categoriesSelect = response;
      }
    )
  }

  initCategoryForm() {
    this.categoryForm = this.fb.group({
      id: new FormControl(''),
      name : new FormControl('', Validators.required),
      alias : new FormControl('', Validators.required),
      image : new FormControl(null, Validators.required),
      enabled : new FormControl(false, Validators.required),
      parent : new FormControl([], Validators.required),
    });
  }

  submit() {
    this.submitted = true;
    if(this.categoryForm.invalid) {
      return;
    }
    this.bindValueToCategoryFormModel();
    this.categoryService.saveCategory(this.categoryFormModel, this.categoryForm.controls["image"].value).subscribe({
      next: (response) => {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This category has been created successfully!";
        this.router.navigate(["categories"]);
      },
      error: () => {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "Error when creating category!";
      }
    })
  }

  private bindValueToCategoryFormModel() {
    this.categoryFormModel.id = this.categoryForm.controls["id"].value;
    this.categoryFormModel.name = this.categoryForm.controls["name"].value;
    this.categoryFormModel.alias = this.categoryForm.controls["alias"].value;
    this.categoryFormModel.enabled = this.categoryForm.controls["enabled"].value;
    this.categoryFormModel.parent = this.categoryForm.controls["parent"].value;
    let categoryId : number = this.categoryForm.controls["parent"].value;
    this.categoryFormModel.parent = new CategorySelect(categoryId);
  } 

  public showPreview(event : any) {
    const file = event.target.files[0];
    if(file) {
      this.categoryForm.patchValue({
        image: file
      });
      this.categoryForm.get('image')?.updateValueAndValidity()
      // File Preview
      const reader = new FileReader();
      reader.onload = () => {
        this.imageURL = reader.result as string;
      }
      reader.readAsDataURL(file);
    } else {
      this.categoryForm.patchValue({
        image: null
      });
      this.imageURL = '';
    }
  }

  get f(): { [key: string]: AbstractControl } {
    return this.categoryForm.controls;
  }

}
