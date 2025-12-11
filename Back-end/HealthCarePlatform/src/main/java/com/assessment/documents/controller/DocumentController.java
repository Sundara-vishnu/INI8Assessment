package com.assessment.documents.controller;

import com.assessment.documents.model.Document;
import com.assessment.documents.service.DocumentService;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin("*")
public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<Document> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(service.upload(file));
    }

    @GetMapping
    public List<Document> list() {
        return service.listAll();
    }
    
    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadByFilename(@PathVariable("filename") String filename) throws MalformedURLException {
        Path path = service.getFilePath(filename);
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) throws IOException {
        service.deleteById(id);
        return ResponseEntity.ok("File deleted");
    }
}
