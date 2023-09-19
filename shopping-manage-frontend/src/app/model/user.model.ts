import { Role } from "./role.model";

export interface User {
    id?: number;
    email?: string;
    photos?: string;
    firstName?: string;
    lastName?: string;
    enabled?: boolean;
    roles? : Role[];
}

export class User implements User{
    constructor(
        public id?: number,
        public email?: string,
        public photos?: string,
        public firstName?: string,
        public lastName?: string,
        public enabled?: boolean,
        public roles? : Role[]
    ) {}
}
