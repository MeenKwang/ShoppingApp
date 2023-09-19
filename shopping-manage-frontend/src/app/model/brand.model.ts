import { CategorySelect } from "./category-select.model";

export interface IBrand {
    id?: number;
    name?: string;
    logo?: string;
    categories?: CategorySelect[];
}

export class Brand implements IBrand {

    constructor(
        public id?: number,
        public name?: string,
        public logo?: string,
        public categories?: CategorySelect[],
        ) {}

}