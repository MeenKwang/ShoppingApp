export interface IBrandSelect {
    id? : number,
    name? : string
}

export class BrandSelect {
    constructor(
        public id? : number,
        public name? : string
    ){}
}