package com.assessment.documents.service;

import com.assessment.documents.model.Document;
import com.assessment.documents.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final DocumentRepository repo;

    public DocumentService(DocumentRepository repo) {
        this.repo = repo;
    }

    public Document upload(MultipartFile file) throws IOException {
    	
        if (file.isEmpty()) throw new IOException("Empty file");
        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.toLowerCase().endsWith(".pdf")) {
            throw new IOException("Only PDF allowed");
        }

        Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(dir);

        String storedName = System.currentTimeMillis() + "-" + originalName.replaceAll("\\s+","_");
        Path target = dir.resolve(storedName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        Document doc = new Document();
        doc.setFilename(storedName);          // <-- correct setter usage
        doc.setSize(file.getSize());
        doc.setCreatedAt(LocalDateTime.now());

        return repo.save(doc);
    }

    public List<Document> listAll() {
        return repo.findAll();
    }

    public Path getFilePath(String filename) {
        return Paths.get(uploadDir).resolve(filename).normalize();
    }

    public void deleteById(Long id) throws IOException {
        Document doc = repo.findById(id).orElseThrow(() -> new IOException("Doc not found"));        
        String filename = doc.getFilename();   
        Path path = getFilePath(filename);
        Files.deleteIfExists(path);
        repo.delete(doc);
    }
}
