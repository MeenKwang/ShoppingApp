import { CategorySelect } from "./category-select.model";

export interface IBrandForm {
    id?: number,
    name?: string,
    logo?: string,
    categories?: CategorySelect[]
}

export class BrandForm implements IBrandForm {
    constructor(
        public id?: number,
        public name?: string,
        public logo?: string,
        public categories?: CategorySelect[]
    ){}
}