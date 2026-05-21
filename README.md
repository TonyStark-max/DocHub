Overview

DocHub is a secure backend system designed to manage team-based document sharing with strict role-based access control. The system enables structured collaboration between Admins, Team Leads, and Employees while enforcing controlled file visibility and secure storage using object storage.
This project focuses on secure API design, authentication architecture, and scalable backend workflows using Spring Boot.

🚀 Core Features

Role-Based Access Control (Admin, Team Lead, Employee)

JWT-based Stateless Authentication

Secure File Upload & Retrieval using MinIO (Object Storage)

Team-level file visibility restrictions

Protected RESTful APIs with filter-based security

Swagger-based API documentation



🏗 Architecture Overview

DocHub follows a layered architecture:
Controller → Service → Repository → Database
Authentication Filter → JWT Validation → Security Context

Security Flow:
User logs in via JWT authentication endpoint.
Token is validated using Spring Security filter.
Role-based authorization is applied at endpoint level.
File access is granted or denied based on role and team mapping.

🛠 Tech Stack

Backend:
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-0db7ed?style=for-the-badge&logo=docker&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)

Security:

JWT Authentication

BCrypt Password Encoding

Role-Based Access Control (RBAC)

Database & Storage:

PostgreSQL / MySQL

MinIO (Object Storage)

Tools:

Maven

Postman

Swagger (OpenAPI)

📂 Project Structure



com.dochub
 ├── controller
 ├── service
 ├── repository
 ├── security
 ├── model
 ├── dto
 └── config

 


The application follows clean separation of concerns and layered architecture principles.


🔐 Role Hierarchy


Role	      Permissions

Admin	      Manage teams, users, full document access

Team Lead   Upload and share files within team

Employee	  Upload files, view team-specific documents


📈 Performance & Validation


Tested 200+ file upload/download operations

Secured 10+ protected endpoints

Implemented stateless authentication (0 server-side sessions)

Validated API responses using Postman collection
