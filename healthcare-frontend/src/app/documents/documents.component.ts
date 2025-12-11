import { Component, OnInit } from '@angular/core';
import { DocumentService, Document } from '../services/document.service';

@Component({
  selector: 'app-documents',
  templateUrl: './documents.component.html',
  styleUrls: ['./documents.component.css']
})
export class DocumentsComponent implements OnInit {

  documents: Document[] = [];
  selectedFile!: File;

  constructor(private service: DocumentService) {}

  ngOnInit(): void {
    this.loadDocuments();
  }

  loadDocuments() {
    this.service.list().subscribe(data => {
      this.documents = data;
    });
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  upload() {
    if (!this.selectedFile) return;

    this.service.upload(this.selectedFile).subscribe(() => {
      alert("Uploaded successfully!");
      this.loadDocuments();
    });
  }

  download(filename: string) {
    this.service.download(filename).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = filename;
      a.click();
    });
  }

  delete(id: number) {
    if (!confirm("Are you sure?")) return;

    this.service.delete(id).subscribe(() => {
      alert("Deleted successfully!");
      this.loadDocuments();
    });
  }
}
