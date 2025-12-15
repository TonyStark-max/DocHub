package com.Document.DocHub.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStats {
    private int totalFiles;
    private long totalSizeInBytes;
    private String totalSizeFormatted;
    private LocalDateTime mostRecentUpload;
    private String mostRecentFileName;
}
