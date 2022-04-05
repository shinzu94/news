import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class HttpService {
    public url = 'http://localhost:8080/';
    public filesDownloadUrl = this.url + 'files/download?fileName=';
    public createNewFileUrl = this.url + 'articles/newFile/'
    private fileListUrl = this.url + 'files/file_list';

    constructor(private http: HttpClient) {
    }

    getFileList() {
        const headersList = this.headers()
            .append('Access-Control-Allow-Methods', 'Get');
        return this.http.get<File[]>(this.fileListUrl, {headers: headersList});
    }

    prepareNewFile(fileName: string) {
        const headersList = this.headers()
            .append('Access-Control-Allow-Methods', 'Post');
        return this.http.get<File>(this.createNewFileUrl + fileName, {headers: headersList});
    }

    private headers() {
        return new HttpHeaders()
            .append('Content-Type', 'application/json')
            .append('Access-Control-Allow-Headers', 'Content-Type')
            .append('Access-Control-Allow-Methods', 'Get')
            .append('Access-Control-Allow-Origin', 'localhost:4200');

    }
}
