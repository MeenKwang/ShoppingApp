export interface ICategorySelect {
    id?: number;
    name?: string;
}

export class CategorySelect implements ICategorySelect {
    constructor(
        public id?: number,
        public name?: string
    ){}
}