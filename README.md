DocHub – Internal Document Management Backend System
Overview

DocHub is a role-based internal document management backend system designed for organizations to securely manage teams, projects, and file sharing with strict access control and auditability.

The system focuses on backend concerns such as authentication, authorization, file permissions, role hierarchy, and secure document access rather than UI interactions.

Core Features
Authentication & Authorization

JWT-based authentication

Role-based authorization using Spring Security

Centralized access control at API and service layers

File Management Design

Files are stored in object storage (MinIO) instead of the database

Metadata (owner, team, permissions) stored in relational DB

Download and upload endpoints are protected by role and team-level checks

Employees cannot directly access cross-team documents

Tech Stack

Language: Java

Framework: Spring Boot

Security: Spring Security, JWT

Database: MySQL / PostgreSQL

Object Storage: MinIO

ORM: JPA / Hibernate

API Documentation: Swagger (OpenAPI)

Build Tool: Maven

Version Control: Git

Key Backend Concepts Demonstrated

Role-Based Access Control (RBAC)

Secure file handling with object storage

JWT authentication flow

Layered backend architecture

Separation of concerns

Authorization at controller + service level

Real-world permission constraints


Future Enhancements

Audit logs for file access and downloads

File versioning

Expiring file access links

Admin-level analytics on document usage

Integration with notification services
