import { CategorySelect } from "./category-select.model";

export interface ICategoryForm {
    id?: number;
    name?: string;
    alias?: string;
    image?: string;
    enabled?: boolean;
    parent? : CategorySelect;
    children? : Set<ICategoryForm>;
}

export class CategoryForm implements ICategoryForm {
    constructor(
        public id?: number,
        public name?: string,
        public alias?: string,
        public image?: string,
        public enabled?: boolean,
        public parent? : CategorySelect,
        public children? : Set<CategoryForm>,
    ) {}
}
