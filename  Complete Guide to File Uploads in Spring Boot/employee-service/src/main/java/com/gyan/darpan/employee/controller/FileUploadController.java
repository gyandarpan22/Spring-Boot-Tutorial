package com.gyan.darpan.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
}
