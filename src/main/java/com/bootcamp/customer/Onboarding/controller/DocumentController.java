package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.DocumentService;
import com.bootcamp.customer.Onboarding.exceptions.ResourceNotFoundException;
import com.bootcamp.customer.Onboarding.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("userId") Long userId) {

        try {
            String result = documentService.processDocument(file,userId) + " Document Uploaded Successfully!!";
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
        }
    }

//    @PutMapping("/verify")
//    public ResponseEntity<Document> verifyDocument(@RequestParam Long userId){
//        Document verifiedDocument = documentService.verifyDocument(userId);
//
//        return ResponseEntity.ok(verifiedDocument);
//    }

    @GetMapping("/status")

    public ResponseEntity<Boolean> isDocumentVerified(@RequestParam Long userId){
        boolean isVerified = documentService.isDocumentVerified(userId);
        return ResponseEntity.ok(isVerified);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDocument(@RequestParam Long userId) {
        try {
            documentService.deleteDocumentByUserId(userId);
            return ResponseEntity.ok("Document deleted successfully");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting document: " + e.getMessage());
        }
    }


}