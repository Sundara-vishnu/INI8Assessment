# Patient Documents Manager - Design Document

## 1. Tech Stack Choices

**Frontend:** Angular 
- Used Angular for building reactive forms and dynamic UI easily. Large community support and seamless integration with REST APIs.

**Backend:** Spring Boot 
- Chosen for building RESTful APIs, handling file uploads, and integrating with databases efficiently. Provides production-ready structure even for small projects.

**Database:** MySQL 
- Reliable relational database for storing file metadata. Easy integration with Spring Boot using JPA.

**Scaling for 1,000 users:** 
- Move file storage to cloud storage like AWS S3 or Azure Blob. 
- Use PostgreSQL for better scalability and concurrency. 
- Implement authentication and role-based access. 
- Add caching for frequently accessed documents.

---

## 2. Architecture Overview

[Angular Frontend] <--HTTP--> [Spring Boot Backend] <--JDBC--> [MySQL Database]
                                  |
                                  v
                               [uploads/ folder]
                               
                            
 
- Frontend handles user interaction (upload/download/delete files). 
- Backend manages business logic, file storage, and database operations. 
- Database stores metadata like filename, size, and upload timestamp. 
- Uploaded PDF files are stored locally in the `uploads/` folder.

---

## 3. API Specification

| Endpoint                | Method | Description           | Sample Request                  | Sample Response                                      |
|-------------------------|--------|---------------------|---------------------------------|-----------------------------------------------------|
| /documents/upload       | POST   | Upload a PDF file    | FormData: file                  | {id, filename, filesize, created_at}               |
| /documents              | GET    | List all documents   | N/A                             | [{id, filename, filesize, created_at}, ...]        |
| /documents/:id          | GET    | Download a file      | N/A                             | PDF File                                            |
| /documents/:id          | DELETE | Delete a file        | N/A                             | {message: "Deleted successfully"}                  |

---

## 4. Data Flow Description

**File Upload Process:** 
1. User selects a PDF file and clicks "Upload". 
2. Frontend sends POST request `/documents/upload` with the file. 
3. Backend validates the file (must be PDF, size limit 10 MB). 
4. Backend stores the file in `uploads/` folder.
5. Backend saves metadata (filename, size, created_at) in MySQL. 
6. Backend sends success response; frontend shows message.

**File Download Process:** 
1. User clicks "Download" on a file. 
2. Frontend sends GET request `/documents/:id`. 
3. Backend retrieves the file from `uploads/` folder. 
4. Backend sends file; browser downloads PDF.

---                              

