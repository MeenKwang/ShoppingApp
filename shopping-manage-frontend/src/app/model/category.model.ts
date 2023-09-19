export interface ICategory {
    id? : number;
    name? : string;
    alias? : string;
    image? : string;
    enabled?: boolean;
    hasChildren? : boolean;
}

export class Category implements ICategory {
    constructor(
        public id? : number,
        public name? : string,
        public alias? : string,
        public image? : string,
        public enabled? : boolean,
        public hasChildren? : boolean
    ){}
}