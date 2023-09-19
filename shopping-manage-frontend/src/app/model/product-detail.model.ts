export interface IProductDetail {
    id?: number;
    name?: string;
    value?: string;
    productId? : string;
}

export class ProductDetail implements IProductDetail {
    constructor(
        public id?: number,
        public name?: string,
        public value?: string,
        public productId? : string
    ){}
}