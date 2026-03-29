package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.DocumentService;
import com.bootcamp.customer.Onboarding.exceptions.ResourceNotFoundException;
import com.bootcamp.customer.Onboarding.model.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DocumentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(documentController).build();
    }


    @Test
    public void testUploadDocumentFailure() throws Exception {
        Long userId = 1L;
        MultipartFile file = new MockMultipartFile("file", "document.pdf", "application/pdf", "test content".getBytes());

        when(documentService.processDocument(any(MultipartFile.class), any(Long.class)))
                .thenThrow(new RuntimeException("Error processing file"));

        mockMvc.perform(multipart("/user/document/upload")
                        .file((MockMultipartFile) file) // Casting to MockMultipartFile
                        .param("userId", userId.toString()))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error processing file: Error processing file"));
    }

//    @Test
//    public void testVerifyDocumentSuccess() throws Exception {
//        Long userId = 1L;
//        Document document = new Document();
//        document.setUserId(userId);
//        document.setType("Aadhaar");
//        document.setStatus(true);
//
//        when(documentService.verifyDocument(userId)).thenReturn(document);
//
//        mockMvc.perform(put("/user/document/verify")
//                        .param("userId", userId.toString()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userId").value(userId))
//                .andExpect(jsonPath("$.type").value("Aadhaar"))
//                .andExpect(jsonPath("$.status").value(true));
//    }

//    @Test
//    public void testVerifyDocumentNotFound() throws Exception {
//        Long userId = 1L;
//
//        // Mock the service to throw ResourceNotFoundException
//        when(documentService.verifyDocument(userId)).thenThrow(new ResourceNotFoundException("Document not found"));
//
//        // Perform the test
//        mockMvc.perform(put("/user/document/verify")
//                        .param("userId", userId.toString()))
//                .andExpect(status().isNotFound())  // Ensure the status is 404
//                .andExpect(content().string("Document not found"));  // Ensure the error message is correct
//    }
//

}
