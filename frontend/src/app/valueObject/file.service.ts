import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class File {
    public readonly name;
    public readonly size;

    constructor(name, size) {
        this.name = name;
        this.size = size;
    }
}
