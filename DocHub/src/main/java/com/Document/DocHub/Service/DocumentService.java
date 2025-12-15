package com.Document.DocHub.Service;

import com.Document.DocHub.Entity.Document;
import com.Document.DocHub.Entity.User;
import com.Document.DocHub.Repositories.DocumentRepo;
import com.Document.DocHub.Repositories.UserRepo;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final MinioClient minioClient;
    private final DocumentRepo documentRepo;
    private final UserRepo userRepo;

    @Value("${minio.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file , String username) throws Exception{
        String objectName=file.getOriginalFilename();
        User user=userRepo.findByEmail(username)
                .orElseGet(()->userRepo.findByName(username)
                        .orElseThrow(()->new RuntimeException("User not found")));

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        Document doc=new Document();
        doc.setFileName(objectName);
        doc.setFileType(file.getContentType());
        doc.setFileUrl("minio://"+bucketName+"/"+objectName);
        doc.setFileSize(file.getSize());
        doc.setUploadedAt(LocalDateTime.now());
        doc.setUploadedBy(user);

        documentRepo.save(doc);
        return "File Saved Successfully"+objectName;
    }

    public Map<String,String> getUploadedBy(String fileName){
        Document doc=documentRepo.findByFileName(fileName)
                .orElseThrow(()->new RuntimeException("File Not Found"));
        String userName=doc.getUploadedBy().getName();

        Map<String,String> result=new HashMap<>();
        result.put("userName ",userName);

        return result;
    }

    public List<Map<String,Object>> getUserUploadedFiles(String username){
        User user=userRepo.findByEmail(username)
                .orElseGet(()->userRepo.findByName(username)
                        .orElseThrow(()->new RuntimeException("User not found:"+username)));
        List<Document> documents=documentRepo.findByUploadedBy(user);

        return documents.stream()
                .map(doc->{
                    Map<String,Object> fileInfo=new HashMap<>();
                    fileInfo.put("fileName", doc.getFileName());
                    fileInfo.put("fileType", doc.getFileType());
                    fileInfo.put("fileSize", doc.getFileSize());
                    fileInfo.put("uploadedAt", doc.getUploadedAt());
                    fileInfo.put("fileUrl", doc.getFileUrl());
                    return fileInfo;

                })
                .collect(Collectors.toList());
    }

    public Map<String,Object> getUserStats(String username){
        User user=userRepo.findByEmail(username)
                .orElseGet(()->userRepo.findByName(username)
                        .orElseThrow(()->new RuntimeException("User not found")));
        List<Document> documents=documentRepo.findByUploadedBy(user);

        int totalFiles= documents.size();

        long totalSizeBytes=documents.stream()
                .mapToLong(Document::getFileSize)
                .sum();

        Document mostRecentDoc=documents.stream()
                .max((doc1,doc2)->doc1.getUploadedAt().compareTo(doc2.getUploadedAt()))
                .orElse(null);

        Map<String,Object> stats=new HashMap<>();
        stats.put("totalFiles",totalFiles);
        stats.put("totalSizeInBytes",totalSizeBytes);

        if(mostRecentDoc!=null) {
            stats.put("mostRecentUpload", mostRecentDoc.getUploadedAt());
            stats.put("mostRecentFileName", mostRecentDoc.getFileName());
        }else{
            stats.put("mostRecentUpload",null);
            stats.put("mostRecentFileName",null);
        }
        return stats;
    }


    @Transactional
    public void deleteFile(String fileName) throws Exception{
        Document doc=documentRepo.findByFileName(fileName)
                .orElseThrow(()->new UsernameNotFoundException("File Not Found"));
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(doc.getFileName())
                            .build()
            );
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        documentRepo.deleteByFileName(fileName);
    }

    public InputStream downloadFile(String fileName) throws Exception{

             return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );

    }
}
