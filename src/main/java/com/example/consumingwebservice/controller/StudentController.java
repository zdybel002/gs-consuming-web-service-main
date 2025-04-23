package com.example.consumingwebservice.controller;

import com.example.consumingwebservice.proxy.StudentClient;
import com.example.consumingwebservice.service.PDFService;
import com.example.consumingwebservice.wsdl.GetAllStudentsResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentClient studentClient;
    private final PDFService pdfService;

    public StudentController(StudentClient studentClient, PDFService pdfService) {
        this.studentClient = studentClient;
        this.pdfService = pdfService;
    }

    // Endpoint do generowania PDF
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generatePdf() throws Exception {
        // Pobieranie danych z SOAP
        GetAllStudentsResponse items = studentClient.getAllStudents();

        // Logowanie danych (pomocne do debugowania)
        items.getStudent().forEach(s -> System.out.println("Student: " + s.getName()));

        // Generowanie PDF
        byte[] pdfBytes = pdfService.generatePdfItems(items);

        // Ustawianie nagłówków odpowiedzi, aby PDF wyświetlał się w przeglądarce
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=students.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        // Zwracanie PDF w odpowiedzi, z nagłówkami i statusem OK
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}

