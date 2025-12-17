package com.Document.DocHub.Controller;

import com.Document.DocHub.Service.DocumentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/doc")
public class DocumentController {
    private final DocumentService docService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file
            )throws Exception
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        String saved=docService.uploadFile(file,username);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/uploadedBy/{fileName}")
    public ResponseEntity<Map<String,String>> getUploadedBy(@PathVariable String fileName){
        return ResponseEntity.ok(docService.getUploadedBy(fileName));
    }

    @GetMapping("/getUploadedFiles")
    public ResponseEntity<List<Map<String,Object>>> getMyUploadedFiles(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

        List<Map<String,Object>> files=docService.getUserUploadedFiles(username);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/myStats")
    public ResponseEntity<Map<String,Object>> getMyStorageStats(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

        Map<String,Object> stats=docService.getUserStats(username);
        return ResponseEntity.ok(stats);
    }

    @DeleteMapping("/DeleteFile/{fileName}")
    public ResponseEntity<String> deleteFiles(@PathVariable String fileName){
        try{
            docService.deleteFile(fileName);
            return ResponseEntity.ok("File Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error While Deleting File"+e);
        }
    }

    @GetMapping("/downloadFile/{fileName}")
    public void downloadFile(@PathVariable String fileName, HttpServletResponse response) throws Exception {

        try(InputStream stream=docService.downloadFile(fileName)){
            String contentType = fileName.endsWith(".webp") ? "image/webp" : "image/jpeg";
            response.setHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");
            response.setContentType(contentType);

            stream.transferTo(response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException("Failed to Download File ",e);
        }
    }
}
