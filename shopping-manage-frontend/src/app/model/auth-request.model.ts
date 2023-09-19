export interface AuthRequest {
    email?: string;
    password?: string;
}

export class AuthRequest implements AuthRequest{

    constructor(
        public email?: string,
        public password?: string
        ) {}

}
