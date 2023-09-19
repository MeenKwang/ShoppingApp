import { Directive, Injectable } from "@angular/core";
import { AbstractControl, AsyncValidator, NG_ASYNC_VALIDATORS, ValidationErrors } from "@angular/forms";
import { map, Observable } from "rxjs";
import { UserService } from "../service/user/user.service";

@Injectable({ providedIn: 'root' })
export class UniqueEmailValidatorDirective implements AsyncValidator {

    constructor(private userService: UserService){}

    validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
        const parent = control && control.parent;
        const idControl = parent?.get('id');
        return this.userService.checkEmailUnique(idControl?.value, control.value).pipe(
            map(valid => {
                return valid ? null : {'uniqueEmail': true};
            })
        );
    }
    
}