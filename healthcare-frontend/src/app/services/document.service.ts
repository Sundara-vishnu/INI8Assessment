import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Document {
  id: number;
  filename: string;
  size: number;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  private baseUrl = 'http://localhost:8080/api/documents';

  constructor(private http: HttpClient) { }

  upload(file: File): Observable<Document> {
    const formData = new FormData();
    formData.append("file", file);
    return this.http.post<Document>(`${this.baseUrl}/upload`, formData);
  }

  list(): Observable<Document[]> {
    return this.http.get<Document[]>(this.baseUrl);
  }

  download(filename: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/download/${filename}`, { responseType: 'blob' });
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${id}`, { responseType: 'text' });
  }
}
