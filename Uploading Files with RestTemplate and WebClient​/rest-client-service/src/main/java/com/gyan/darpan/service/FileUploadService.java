package com.gyan.darpan.service;

import com.gyan.darpan.client.RestClient;
import com.gyan.darpan.client.RestWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

@Service
public class FileUploadService {
    private final RestClient restClient;
    private final String baseUrl;
    private final RestWebClient restWebClient;

    public FileUploadService(RestClient restClient,
                             RestWebClient restWebClient,
                             @Value("${employee.service.base-url}") String baseUrl) {
        this.restClient = restClient;
        this.restWebClient = restWebClient;
        this.baseUrl = baseUrl;
    }

    public void uploadFile() {
        String resourceUrl = "file/upload";

        //set content-type and accept
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));

        //create body object
        String filePath = "/Users/user1/Downloads/springboot.png";
        File file = new File(filePath);
        FileSystemResource fileSystemResource = new FileSystemResource(file);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileSystemResource);

        ResponseEntity<String> responseEntity = restWebClient.execute(baseUrl, resourceUrl, String.class,
                null, headers, HttpMethod.POST, null, body);


        System.out.println("Status : " + responseEntity.getStatusCode());
        System.out.println("Response: " + responseEntity.getBody());

    }

    public void uploadFileInputStream() throws IOException {
        String resourceUrl = "file/upload";

        //set content-type and accept
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));

        //create body object
        String filePath = "/Users/user1/Downloads/springboot.png";
        byte[] data = Files.readAllBytes(Paths.get(filePath));

        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            //ByteArrayResource
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream) {
                @Override
                public String getFilename() {
                    return "springboot.png";
                }

                @Override
                public long contentLength() throws IOException {
                    return data.length;
                }
            };

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", inputStreamResource);

            ResponseEntity<String> responseEntity = restWebClient.execute(baseUrl, resourceUrl, String.class,
                    null, headers, HttpMethod.POST, null, body);


            System.out.println("Status : " + responseEntity.getStatusCode());
            System.out.println("Response: " + responseEntity.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
