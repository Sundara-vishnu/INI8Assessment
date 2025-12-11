# Patient Documents Manager

## Project Overview

Patient Documents Manager is a simple full-stack application that allows patients to:

- Upload PDF medical documents (prescriptions, test results, etc.)
- View a list of uploaded documents
- Download any document
- Delete a document when no longer needed

This project is built using **Angular** (frontend), **Spring Boot** (backend), and **MySQL** (database). Uploaded PDFs are stored locally in the `uploads/` folder.

---

## Folder Structure

PatientDocumentsManager/
├── frontend/ # Angular frontend source code
├── backend/ # Spring Boot backend source code
├── design.md # Design document
├── README.md # This file
└── .gitignore # Files/folders ignored by Git



---

## Running Locally

### Backend (Spring Boot)
```bash
cd backend
./mvnw spring-boot:run

