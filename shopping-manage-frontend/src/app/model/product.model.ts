import { BrandSelect, IBrandSelect } from "./brand-select.model";
import { CategorySelect, ICategorySelect } from "./category-select.model";
import { IProductDetail, ProductDetail } from "./product-detail.model";
import { IProductImage, ProductImage } from "./product-image.model";

export interface IProduct {
    id?: number;
    name?: string;
    alias?: string;
    brand?: IBrandSelect;
    category?: ICategorySelect;
    enabled?: boolean;
    inStock?: boolean;
    cost?: number;
    price?: number;
    discountPercent?: number;
    shortDescription?: string;
    fullDescription?: string;
    length?: number;
    width?: number;
    height?: number;
    weight?: number;
    mainImage?: string;
    extraImages?: Array<IProductImage>;
    productDetails?: Array<IProductDetail>;
}

export class Product implements IProduct {
    constructor(
        public id? : number,
        public name? : string,
        public alias?: string,
        public brand? : BrandSelect,
        public category? : CategorySelect,
        public enabled? : boolean,
        public inStock?: boolean,
        public cost?: number,
        public price?: number,
        public discountPercent?: number,
        public shortDescription?: string,
        public fullDescription?: string,
        public length?: number,
        public width?: number,
        public height?: number,
        public weight?: number,
        public mainImage?: string,
        public extraImages?: Array<ProductImage>,
        public productDetails?: Array<ProductDetail>
    ) {}
}