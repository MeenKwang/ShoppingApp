import { Role } from "./role.model";

export interface UserForm {
    id?: number;
    email?: string;
    photos?: string;
    firstName?: string;
    lastName?: string;
    password?: string;
    enabled?: boolean;
    roles? : Role[];
}

export class UserForm implements UserForm {
    constructor(
        public id?: number,
        public email?: string,
        public photos?: string,
        public firstName?: string,
        public lastName?: string,
        public password?: string,
        public enabled?: boolean,
        public roles? : Role[],
    ) {}
}
