package com.gyan.darpan.service;

import com.gyan.darpan.client.RestClient;
import com.gyan.darpan.client.RestWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    public void downloadFile() {
        String resourceUrl = "download/file/{fileName}";

        Map<String, Object> pathVariables = new HashMap<>();
        pathVariables.put("fileName", "springboot.png");

        ResponseEntity<ByteArrayResource> responseEntity = restWebClient.execute(baseUrl, resourceUrl, ByteArrayResource.class, null,
                null, HttpMethod.GET, pathVariables, null);

        System.out.println(responseEntity.getStatusCode());

        String filePath = "/Users/user1/Downloads/springboot.png";
        try (OutputStream outputStream = new FileOutputStream(filePath)) {

            outputStream.write(responseEntity.getBody().getByteArray());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void downloadFile1() {
        String resourceUrl = "download/file/{fileName}";

        Map<String, Object> pathVariables = new HashMap<>();
        pathVariables.put("fileName", "springboot.png");

        ResponseEntity<Resource> responseEntity = restWebClient.execute(baseUrl, resourceUrl, Resource.class, null,
                null, HttpMethod.GET, pathVariables, null);

        System.out.println(responseEntity.getStatusCode());

        String filePath = "/Users/user1/Downloads/springboot.png";
        try (InputStream inputStream = responseEntity.getBody().getInputStream();
             OutputStream outputStream = new FileOutputStream(filePath)) {

            byte[] data = new byte[4026];
            int byteRead = 0;
            while ((byteRead = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, byteRead);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
