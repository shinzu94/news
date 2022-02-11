import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class HttpService {
    public url = 'http://localhost:8080/';
    public filesUrl = this.url + 'files/';
    public filesDownloadUrl = this.url + 'files/download?fileName=';


    private fileListUrl = this.url + 'files/file_list';

    constructor(private http: HttpClient) {
    }

    getFileList() {
        const headers = new HttpHeaders()
            .append('Content-Type', 'application/json')
            .append('Access-Control-Allow-Headers', 'Content-Type')
            .append('Access-Control-Allow-Methods', 'GET')
            .append('Access-Control-Allow-Origin', 'localhost:4200');
        return this.http.get<File[]>(this.fileListUrl, {headers: headers});
    }
}
