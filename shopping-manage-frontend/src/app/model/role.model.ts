export interface Role {
    id?: number;
    name?: string;
    description?: string;
}

export class Role implements Role {

    constructor(
        public id?: number,
        public name?: string,
        public description?: string
        ) {}

}
