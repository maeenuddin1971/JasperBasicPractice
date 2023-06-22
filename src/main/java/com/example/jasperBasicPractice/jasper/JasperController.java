package com.example.jasperBasicPractice.jasper;

import com.example.jasperBasicPractice.PersonDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jasperReq")
public class JasperController {

    @Autowired
    private JasperService jasperService;

    @GetMapping("/generateReport")
    public ResponseEntity<byte[]> generateReport() throws JRException, IOException {
        // Load the Jasper report file
        InputStream jasperStream = new ClassPathResource("reports/Maeen_first_jasper.jasper").getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        // Set the report parameters
        Map<String, Object> parameters = new HashMap<>();
        // Add any required parameters to the map

        // will add value to the variable as field value of the jasper
        List<PersonDTO> personDTOList = new ArrayList<>();
        personDTOList.add(getPersonDTO());
        JRDataSource dataSource = new JRBeanCollectionDataSource(personDTOList);


        // Compile and fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export the report to PDF
        byte[] reportBytes = jasperService.exportReportToPdf(jasperPrint);

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "report.pdf");

        // Return the report as a ResponseEntity
        return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
    }

    public PersonDTO getPersonDTO() {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setFirstName("pavel rahaman");
        return personDTO;
    }
}
