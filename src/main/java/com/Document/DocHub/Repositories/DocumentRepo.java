package com.Document.DocHub.Repositories;

import com.Document.DocHub.DTO.DocDto;
import com.Document.DocHub.Entity.Document;
import com.Document.DocHub.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface DocumentRepo extends JpaRepository<Document,Integer> {
    Optional<Document> findByFileName(String fileName);

    List<Document> findByUploadedBy(User user);

    List<Document> findByUploadedById(Integer userId);

    List<Document> findByUploadedByOrderByUploadedAtDesc(User user);

    Optional<Document> deleteByFileName(String fileName);
}
