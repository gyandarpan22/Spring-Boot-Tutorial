package com.gyan.darpan.employee.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
public class FileUploadController {

    @PostMapping(path = "file/upload", produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }
        try {
            System.out.println(
                    "Original File Name : " + file.getOriginalFilename()
                            + "File Size : " + file.getSize()
                            + "Content Type : " + file.getContentType()
            );

            String filePath = "/Users/user1/Downloads/Demo";

            File file1 = new File(filePath + File.separator + file.getOriginalFilename());

            file.transferTo(file1);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("File uploaded succesfully.", HttpStatus.OK);

    }

    @PostMapping(path = "multi-file/upload", produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadMultiFile(
            @RequestParam Map<String, MultipartFile> multipartFileMap
    ) {
        try {
            for (MultipartFile file : multipartFileMap.values()) {
                System.out.println(
                        "Original File Name : " + file.getOriginalFilename()
                                + "File Size : " + file.getSize()
                                + "Content Type : " + file.getContentType()
                );

                String filePath = "/Users/user1/Downloads/Demo";

                File file1 = new File(filePath + File.separator + file.getOriginalFilename());

                file.transferTo(file1);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("File uploaded succesfully.", HttpStatus.OK);

    }


    @GetMapping(path = "download/file/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
        try {
            String filePath = "/Users/user1/Downloads/Demo";

            Path path = Paths.get(filePath + File.separator + fileName);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {

                String contentType = Files.probeContentType(path);
                if (contentType == null) {
                    contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
                }

                ContentDisposition contentDisposition = ContentDisposition.attachment()
                        .filename(resource.getFilename())
                        .build();

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);

            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.notFound().build();
    }

}
