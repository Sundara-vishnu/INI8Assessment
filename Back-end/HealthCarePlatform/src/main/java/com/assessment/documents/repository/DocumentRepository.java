package com.assessment.documents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.documents.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
         
}
