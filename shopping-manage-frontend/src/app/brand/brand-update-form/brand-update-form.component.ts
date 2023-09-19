import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, AbstractControl } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NotifyModalComponent } from 'src/app/modal/notify-modal/notify-modal.component';
import { BrandForm } from 'src/app/model/brand-form.model';
import { CategoryForm } from 'src/app/model/category-form-model';
import { CategorySelect } from 'src/app/model/category-select.model';
import { BrandService } from 'src/app/service/brand/brand.service';
import { CategoryService } from 'src/app/service/category/category.service';

@Component({
  selector: 'app-brand-update-form',
  templateUrl: './brand-update-form.component.html',
  styleUrls: ['./brand-update-form.component.css']
})
export class BrandUpdateFormComponent implements OnInit {

  id: number | any;
  categoriesSelect : Array<CategorySelect> | any;
  brandForm! : FormGroup;
  imageURL: string = '';
  submitted: boolean = false;
  brandFormModel: BrandForm = {};
  categoriesListSelected : string[] = [];

  constructor(
    private brandService: BrandService,
    private categoryService: CategoryService,
    private fb: FormBuilder,
    private router: Router,
    private modalService: NgbModal,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

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
    this.getForm();
  }

  getForm() {
    const subcription = this.brandService.getBrandById(this.id).subscribe(
      (response : BrandForm) => {
        this.brandFormModel = response;
        //patchValue
        this.brandForm.patchValue({id: this.brandFormModel.id});
        this.brandForm.patchValue({name: this.brandFormModel.name});
        this.brandForm.patchValue({logo: this.brandFormModel.logo});
        //set url of brand logo
        this.imageURL = "brand-logos/" + this.id + "/" + this.brandFormModel.logo;
        //set categories as pre-selected option categories in ng-select
        //patchValue
        this.brandFormModel.categories?.forEach((category) => {
          let brandId : number | any = category.id;
          this.categoriesListSelected.push(brandId);
        })
        this.brandForm.patchValue({categories: this.categoriesListSelected});
        // unsubscribe
        subcription.unsubscribe();
      }
    );
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
        modalRefNotify.componentInstance.message = "This brand has been update successfully!";
        this.router.navigate(["brands"]);
      },
      error: () => {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "Error when updating brand!";
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
