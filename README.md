# DocHub — Secure Media Storage Platform

---

## Overview

DocHub is a secure full-stack media storage platform built using Spring Boot, MySQL, and MinIO Object Storage.

The application is designed to solve the limitations of storing large media files directly inside relational databases using BLOB storage. Instead of storing images and videos inside the database, the system separates:

* File metadata → stored in MySQL
* Media objects → stored in MinIO Object Storage

This architecture improves scalability, reduces database overhead, and provides better handling for large media content.

---

# Problem Statement

Traditional applications often store images and videos directly inside relational databases using BLOB columns. While possible, this approach introduces several architectural problems:

* Increased database size
* Slower query performance
* Heavy backup operations
* Inefficient handling of binary data
* Poor scalability for large media content

DocHub addresses this problem using object storage architecture by integrating MinIO for storing media files while maintaining metadata separately in MySQL.

---

# System Architecture

```text
Frontend Client
       │
       ▼
Spring Boot REST APIs
       │
 ┌────────────────────┐
 │  Spring Security   │
 │   JWT Validation   │
 └────────────────────┘
       │
 ┌────────────────────┐       ┌────────────────────┐
 │       MySQL        │       │       MinIO        │
 │   Metadata Store   │       │   Object Storage   │
 └────────────────────┘       └────────────────────┘
```

---

# Features

* Secure image and video upload
* File retrieval using protected APIs
* Object storage integration using MinIO
* Metadata management using MySQL
* JWT-based stateless authentication
* Role-Based Access Control (RBAC)
* Protected REST APIs using Spring Security filters
* Swagger/OpenAPI documentation
* Layered backend architecture
* Secure file access based on authentication and authorization

---

# Tech Stack

## Backend
<p align="left">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/>
  <img src="https://img.shields.io/badge/SpringDataJPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>
  <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"/>
</p>

## Database & Storage
<p align="left">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/MinIO-C72E49?style=for-the-badge&logo=minio&logoColor=white"/>
</p>

## Security
<p align="left">
  <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens"/>
  <img src="https://img.shields.io/badge/BCrypt-003A70?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/RBAC-5C2D91?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/StatelessAuth-FF6F00?style=for-the-badge"/>
</p>

## Tools
<p align="left">
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
  <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"/>
  <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black"/>
</p>

---

# Security Flow

1. User logs in using credentials
2. JWT token is generated after successful authentication
3. Token is sent in request headers
4. Spring Security filter validates the token
5. Security context is established
6. Protected endpoints are accessed based on user roles

---

# Storage Design

The application follows a hybrid storage architecture.

| Data Type       | Storage Location |
| --------------- | ---------------- |
| File Metadata   | MySQL            |
| Images & Videos | MinIO            |

## Metadata Stored in MySQL

* File name
* File type
* Upload timestamp
* User ownership
* Object reference key

## Media Files Stored in MinIO

* Images
* Videos
* Binary media objects

This separation improves storage efficiency and reduces database load.

---

# Project Structure

```text
com.dochub
│
├── controller
├── service
├── repository
├── security
├── config
├── model
├── dto
└── exception
```

The project follows layered architecture and separation of concerns for better maintainability and scalability.

---

# API Documentation

Swagger/OpenAPI is integrated for testing and exploring APIs.

## Example Endpoints

| Method | Endpoint      | Description          |
| ------ | ------------- | -------------------- |
| POST   | /auth/login   | User authentication  |
| POST   | /files/upload | Upload media file    |
| GET    | /files/{id}   | Retrieve media file  |
| DELETE | /files/{id}   | Delete file          |
| GET    | /files/user   | Fetch uploaded files |

---

# Performance & Validation

* Tested secure upload and download workflows
* Protected multiple REST endpoints using JWT filters
* Implemented stateless authentication with zero server-side sessions
* Validated APIs using Postman collections
* Successfully handled authenticated concurrent requests

---

# Why MinIO Instead of BLOB Storage

MinIO was chosen because object storage systems are better suited for handling large binary media content compared to relational databases.

## Advantages

* Better scalability
* Reduced database overhead
* Faster media handling
* Easier storage management
* Separation of structured and unstructured data
* Cloud-compatible object storage architecture

---

# Future Improvements

* Presigned URL support
* File sharing between users
* Redis caching
* File compression & optimization
* Audit logging system

---

# Running Locally

```bash
git clone <repository-url>

cd dochub

docker-compose up
```

---

# Author

Developed as a backend-focused project to explore:

* Object Storage Architecture
* Secure REST API Design
* JWT Authentication
* Scalable Media Storage Systems
* Spring Security Integration
