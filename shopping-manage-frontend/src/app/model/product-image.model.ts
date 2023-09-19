export interface IProductImage {
    id?: number;
    name?: string;
    productId?: string;
}

export class ProductImage implements IProductImage {
    constructor(
        public id? : number,
        public name? : string,
        public productId? : string,
        public order? : number
    ) {}
}