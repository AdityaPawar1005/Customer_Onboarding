package com.bootcamp.customer.Onboarding.service;

import com.bootcamp.customer.Onboarding.Repository.DocumentRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.Service.DocumentService;
import com.bootcamp.customer.Onboarding.Service.NotificationService;
import com.bootcamp.customer.Onboarding.exceptions.ResourceNotFoundException;
import com.bootcamp.customer.Onboarding.model.Document;
import com.bootcamp.customer.Onboarding.model.User;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testVerifyDocument_DocumentNotFound() {
        Long userId = 1L;

        when(documentRepository.findByUserId(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            documentService.verifyDocument(userId);
        });

        assertEquals("Document not found for userId:1", exception.getMessage());
    }

    @Test
    void testIsDocumentVerified() {
        Long userId = 1L;
        Document document = new Document(userId, new User(), "Aadhaar");
        document.setStatus(true);

        when(documentRepository.findByUserId(userId)).thenReturn(Optional.of(document));

        boolean result = documentService.isDocumentVerified(userId);

        assertTrue(result);
    }

    @Test
    void testIsDocumentVerified_DocumentNotFound() {
        Long userId = 1L;

        when(documentRepository.findByUserId(userId)).thenReturn(Optional.empty());

        boolean result = documentService.isDocumentVerified(userId);

        assertFalse(result);
    }
}
