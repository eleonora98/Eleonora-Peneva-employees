import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PairResult } from '../model/pair-result';

@Injectable({
    providedIn: 'root'
})
export class EmployeeService {

    constructor(private http: HttpClient) { }

    upload(file: File): Observable<PairResult[]> {
        const formData = new FormData();

        formData.append('file', file);

        return this.http.post<any[]>("/api/employees/upload", formData);
    }
}