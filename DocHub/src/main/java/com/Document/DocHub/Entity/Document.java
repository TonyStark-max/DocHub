package com.Document.DocHub.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String fileName;
    private long fileSize;
    private String fileType;
    private String fileUrl;
    private LocalDateTime uploadedAt;
    @ManyToOne
    private User uploadedBy;
}
