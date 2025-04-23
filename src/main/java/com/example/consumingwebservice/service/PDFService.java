package com.example.consumingwebservice.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import com.example.consumingwebservice.wsdl.GetAllStudentsResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.springframework.stereotype.Service;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Service class responsible for generating PDF documents from XML data using XSLT transformations.
 * This service uses Apache FOP (Formatting Objects Processor) to generate PDFs from the XML data provided.
 */
@Service
public class PDFService {

    private final FopFactory fopFactory = FopFactory.newInstance(new java.io.File(".").toURI());

    public byte[] generatePdfItems(GetAllStudentsResponse itemsResponse) throws Exception {

        System.out.println("Students count: " + itemsResponse.getStudent().size());

        // Marshal Java object to XML
        JAXBContext jaxbContext = JAXBContext.newInstance(GetAllStudentsResponse.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(itemsResponse, stringWriter);
        String xmlContent = stringWriter.toString();

        // Load XSLT from resources (e.g. src/main/resources/xslt/students.xsl)
        InputStream xsltInputStream = getClass().getClassLoader().getResourceAsStream("students.xsl");
        if (xsltInputStream == null) {
            throw new IllegalArgumentException("XSLT file not found in classpath under xslt/students.xsl");
        }

        // Prepare XML source
        ByteArrayInputStream xmlInputStream = new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8));
        StreamSource xmlSource = new StreamSource(xmlInputStream);

        // Set up transformer
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsltInputStream));

        // Set up FOP for PDF output
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, pdfOutputStream);

        // Transform XML + XSL into PDF
        SAXResult saxResult = new SAXResult(fop.getDefaultHandler());
        transformer.transform(xmlSource, saxResult);

        // Return PDF as byte[]
        return pdfOutputStream.toByteArray();
    }
}
