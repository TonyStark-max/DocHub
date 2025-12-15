package com.Document.DocHub.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocDto {
    private Integer id;
    private String fileName;
    private long fileSize;
    private String fileType;
    private String fileUrl;
    private String uploadedBy;

}

