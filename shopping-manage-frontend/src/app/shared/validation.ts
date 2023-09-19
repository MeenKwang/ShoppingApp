import { AbstractControl, AsyncValidator, AsyncValidatorFn, ValidatorFn } from '@angular/forms';
const PASSWORD_PATTERN = /^(?=.*[!@#$%^&*]+)[a-zA-Z0-9!@#$%^&*]/;
export default class Validation {
  
  static match(controlName: string, checkControlName: string): ValidatorFn {
    return (controls: AbstractControl) => {
      const control = controls.get(controlName);
      const checkControl = controls.get(checkControlName);

      if (checkControl?.errors && !checkControl.errors['matching']) {
        return null;
      }

      if (control?.value !== checkControl?.value) {
        controls.get(checkControlName)?.setErrors({ matching: true });
        return { matching: true };
      } else {
        return null;
      }
    };
  }

  static passwordCheck(): ValidatorFn {
    return (controls: AbstractControl) => {
      if(controls.value !== null && controls.value !== '') {
        if(controls.value.length < 6) {
          return { minLengthRequired : true };
        } 
        if(controls.value.length > 32) {
          return { maxLengthRequired : true };
        } 
        if(!controls.value.toString().match(PASSWORD_PATTERN)) {
          return { patternRequired : true };
        } 
        return null;
      } 
      return null;
    };
  }
}