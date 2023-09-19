import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators, AbstractControl } from '@angular/forms';
import { Router, NavigationExtras, ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { debounceTime, interval, map, switchMap } from 'rxjs';
import { NotifyModalComponent } from 'src/app/modal/notify-modal/notify-modal.component';
import { Role } from 'src/app/model/role.model';
import { UserForm } from 'src/app/model/user-form.model';
import { RoleService } from 'src/app/service/role/role.service';
import { UserService } from 'src/app/service/user/user.service';
import { UniqueEmailValidatorDirective } from 'src/app/shared/unique-email-validator.directive';
import Validation from 'src/app/shared/validation';

@Component({
  selector: 'app-user-update-form',
  templateUrl: './user-update-form.component.html',
  styleUrls: ['./user-update-form.component.css']
})
export class UserUpdateFormComponent implements OnInit {

  id: number | any;
  firstName: string | any;
  lastName: string | any;

  userForm!: FormGroup;
  userFormModel!: UserForm;
  roleList!: Role[] | any;
  roleListSelected : string[] = [];
  imageURL: string | any = '';
  submitted = false;

  constructor(
    private fb: FormBuilder,
    private roleService: RoleService,
    private userService: UserService,
    private router: Router,
    private uniqueEmailValidatorDirective: UniqueEmailValidatorDirective,
    private route: ActivatedRoute,
    private modalService: NgbModal,
  ) { }

  ngOnInit(): void {

    const PASSWORD_PATTERN = /^(?=.*[!@#$%^&*]+)[a-zA-Z0-9!@#$%^&*]/;

    this.id = this.route.snapshot.params['id'];

    this.getForm();

    this.getAllRoles(); 

    const emailControl = new FormControl('', Validators.compose([
        Validators.required,
        Validators.email
      ]), this.uniqueEmailValidatorDirective.validate.bind(this.uniqueEmailValidatorDirective)
    )

    this.userForm = this.fb.group({
      id: new FormControl(this.id),
      email: emailControl,
      password: new FormControl('', {validators: Validators.compose([
        Validation.passwordCheck()
      ])}),
      confirmPassword: new FormControl(''),
      firstName: new FormControl('', {validators:[
        Validators.required,
        Validators.minLength(3)
      ]}),
      lastName: new FormControl('', {validators:[
        Validators.required,
        Validators.minLength(3)
      ]}),
      enabled: new FormControl(false, {validators:[
        Validators.required,
      ]}),
      roles: new FormControl(this.roleListSelected, {validators:[
        Validators.required,
      ]}),
      avatar: new FormControl(null, {validators:[

      ]}),
    }, {validators: [Validation.match('password', 'confirmPassword')]}
    );
  }

  getForm() {
    const subcription = this.userService.getUserById(this.id).subscribe(
      (response : UserForm) => {
        this.userFormModel = response;
        //patchValue
        this.userForm.patchValue({id: this.userFormModel.id});
        this.userForm.patchValue({email: this.userFormModel.email});
        this.userForm.patchValue({firstName: this.userFormModel.firstName});
        this.userForm.patchValue({lastName: this.userFormModel.lastName});
        this.userForm.patchValue({enabled: this.userFormModel.enabled});
        //set url of user photos
        this.imageURL = "user-photos/" + this.id + "/" + this.userFormModel.photos;
        //set roles of users as pre-selected option roles in ng-select
        this.userFormModel.roles?.forEach(role => {
          let roleId : number | any = role.id;
          this.roleListSelected.push(roleId);
        });
        //patchValue
        this.userForm.patchValue({roles: this.roleListSelected});
        // unsubscribe
        subcription.unsubscribe();
      }
    );
  }

  getAllRoles() {
    this.roleService.getAllRoles().subscribe(
      (response : Role[]) => {
        this.roleList = response;
      }
    )
  }

  private bindValueToUserFormModel() {
    this.userFormModel.id = this.id;
    this.userFormModel.email = this.userForm.controls["email"].value;
    this.userFormModel.firstName = this.userForm.controls["firstName"].value;
    this.userFormModel.lastName = this.userForm.controls["lastName"].value;
    this.userFormModel.password = this.userForm.controls["password"].value;
    this.userFormModel.enabled = this.userForm.controls["enabled"].value;
    this.userFormModel.roles = [];
    let roleIdList : [] = this.userForm.controls["roles"].value;
    roleIdList.forEach((item) => {
      this.userFormModel.roles?.push(new Role(item));
    }) 
  } 

  submit() {
    this.submitted = true;
    this.bindValueToUserFormModel();
    if(this.userForm.invalid) {
      console.log(this.findInvalidControls());
      return;
    }
    console.log(this.userFormModel);
    this.userService.create(this.userFormModel, this.userForm.controls["avatar"].value).subscribe({
      next: (response) => {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This user has been update successfully!"
        this.router.navigate(["users"]);
      },
      error: () => {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "Error when updating user!";
      }
    });
  }

  public findInvalidControls() {
    const invalid = [];
    const controls = this.userForm.controls;
    for (const name in controls) {
        if (controls[name].invalid) {
            invalid.push(name);
        }
    }
    return invalid;
}

  public showPreview(event : any) {
    const file = event.target.files[0];
    if(file) {
      this.userForm.patchValue({
        avatar: file
      });
      this.userForm.get('avatar')?.updateValueAndValidity()
      // File Preview
      const reader = new FileReader();
      reader.onload = () => {
        this.imageURL = reader.result as string;
      }
      reader.readAsDataURL(file);
    } else {
      this.userForm.patchValue({
        avatar: null
      });
      this.imageURL = '';
    }
  }

  get f(): { [key: string]: AbstractControl } {
    return this.userForm.controls;
  }

}
