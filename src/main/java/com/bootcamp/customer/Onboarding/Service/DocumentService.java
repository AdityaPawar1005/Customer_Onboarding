package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.Repository.DocumentRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.exceptions.ResourceNotFoundException;
import com.bootcamp.customer.Onboarding.model.Document;
import com.bootcamp.customer.Onboarding.model.User;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AdhaarService adhaarService;

    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    public String processDocument(MultipartFile file, Long userId) throws IOException, TesseractException {
        // Save the file to a temporary location
        Path tempFilePath = Paths.get(TEMP_DIR, file.getOriginalFilename());
        Files.write(tempFilePath, file.getBytes());

        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\CustomerOnboarding\\Customer_Onboarding\\src\\main\\resources\\TrainingData"); // Adjust path based on your OS and Tesseract installation

        String text = tesseract.doOCR(tempFilePath.toFile());

        String aadhaarNumber = extractAadhaarNumber(text);
        String name = extractName(text);

        Files.delete(tempFilePath);
        saveDocument(userId, "Aadhaar", false);
        boolean isDocVaild = adhaarService.isAadhaarExists(aadhaarNumber);

        if(isDocVaild) {
            verifyDocument(userId);
            return String.format("Aadhaar Number: %s is verified ", aadhaarNumber);
        }

        else
            return String.format("Aadhaar Number: %s is not valid", aadhaarNumber);

    }

    private void saveDocument(Long userId, String type, boolean status) {
        // Find the user by userId
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new ResourceNotFoundException("User not found for userId: " + userId);
        }

        User user = userOpt.get();

        Document document = new Document();
        document.setUser(user);
        document.setType(type);
        document.setStatus(status);
        documentRepository.save(document);
    }

    private String extractAadhaarNumber(String text) {
        // Regex for Aadhaar number (assuming the format is XXXX XXXX XXXX)
        Pattern pattern = Pattern.compile("\\d{4}\\s\\d{4}\\s\\d{4}");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : "Not found";
    }

    private String extractName(String text) {
        // Regex for name extraction (assuming it starts with 'Name:' and is followed by the name)
        Pattern pattern = Pattern.compile("Name\\s*:\\s*(.*)");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : "Not found";
    }

    // Code to make the user's document status verified
   /* public Document verifyDocument(Long userId){
        Optional<Document> documentOpt = documentRepository.findByUserId(userId);

        if(documentOpt.isPresent()){
            Document document = documentOpt.get();
            document.setStatus(true);
            // code to send notification for document verification
            notificationService.documentVerificationSuccess(userId);

            // code to send notification for document verification
            notificationService.notificationSentForUser(userId);
            return documentRepository.save(document);
        }else{
            throw new ResourceNotFoundException("Document not found for userId:" + userId);
        }
    }*/

    public Document verifyDocument(Long userId) {
        Optional<Document> documentOpt = documentRepository.findByUserId(userId);

        if (documentOpt.isPresent()) {
            Document document = documentOpt.get();
            document.setStatus(true);
            notificationService.documentVerificationSuccess(userId);
            notificationService.notificationSentForUser(userId);
            return documentRepository.save(document);
        } else {
            throw new ResourceNotFoundException("Document not found for userId:" + userId);
        }
    }


    //Code to check the status of the document
    public boolean isDocumentVerified(Long userId){
        Optional<Document> documentOpt = documentRepository.findByUserId(userId);
        if (documentOpt.isPresent()) {
            Document document = documentOpt.get();
            return document.isStatus();
        }
        return false;

    }

    public void deleteDocumentByUserId(Long userId) {
        Optional<Document> documentOpt = documentRepository.findByUserId(userId);
        if (documentOpt.isPresent()) {
            documentRepository.delete(documentOpt.get());
        } else {
            throw new ResourceNotFoundException("Document not found for userId: " + userId);
        }
    }


}