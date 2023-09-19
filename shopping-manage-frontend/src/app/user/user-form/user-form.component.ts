import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { NavigationExtras, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, map, of } from 'rxjs';
import { NotifyModalComponent } from 'src/app/modal/notify-modal/notify-modal.component';
import { Role } from 'src/app/model/role.model';
import { UserForm } from 'src/app/model/user-form.model';
import { RoleService } from 'src/app/service/role/role.service';
import { UserService } from 'src/app/service/user/user.service';
import { UniqueEmailValidatorDirective } from 'src/app/shared/unique-email-validator.directive';
import Validation from 'src/app/shared/validation';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {

  userForm!: FormGroup;
  userFormModel: UserForm = {};
  roleList!: Role[];
  imageURL: string = '';
  submitted = false;

  constructor(
    private fb: FormBuilder,
    private roleService: RoleService,
    private userService: UserService,
    private router: Router,
    private uniqueEmailValidatorDirective: UniqueEmailValidatorDirective,
    private modalService: NgbModal,
  ) { }

  ngOnInit(): void {

    const PASSWORD_PATTERN = /^(?=.*[!@#$%^&*]+)[a-zA-Z0-9!@#$%^&*]/;



    this.getAllRoles(); 

    const emailControl = new FormControl('', Validators.compose([
        Validators.required,
        Validators.email
      ]), this.uniqueEmailValidatorDirective.validate.bind(this.uniqueEmailValidatorDirective)
    )

    this.userForm = this.fb.group({
      id: new FormControl(''),
      email: emailControl,
      password: new FormControl('', {validators: Validators.compose([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(32),
        Validators.pattern(PASSWORD_PATTERN)
      ])}),
      confirmPassword: new FormControl('', {validators: [
        Validators.required,
        Validators.pattern(PASSWORD_PATTERN)
      ]}),
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
      roles: new FormControl([], {validators:[
        Validators.required,
      ]}),
      avatar: new FormControl(null, {validators:[
        Validators.required,
      ]}),
    }, {validators: [Validation.match('password', 'confirmPassword')]}
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
    this.userFormModel.email = this.userForm.controls["email"].value;
    this.userFormModel.firstName = this.userForm.controls["firstName"].value;
    this.userFormModel.lastName = this.userForm.controls["lastName"].value;
    this.userFormModel.password = this.userForm.controls["password"].value;
    this.userFormModel.enabled = this.userForm.controls["enabled"].value;
    this.userFormModel.roles = this.userForm.controls["roles"].value;
  } 

  submit() {
    this.submitted = true;
    if(this.userForm.invalid) {
      return;
    }
    this.bindValueToUserFormModel();
    this.userService.create(this.userFormModel, this.userForm.controls["avatar"].value).subscribe({
      next: (response) => {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "This user has been created successfully!"
        this.router.navigate(["users"]);
      },
      error: () => {
        const modalRefNotify = this.modalService.open(NotifyModalComponent);
        modalRefNotify.componentInstance.message = "Error when creating user!";
      }
    });
  }

//   public findInvalidControls() {
//     const invalid = [];
//     const controls = this.userForm.controls;
//     for (const name in controls) {
//         if (controls[name].invalid) {
//             invalid.push(name);
//         }
//     }
//     return invalid;
// }

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
