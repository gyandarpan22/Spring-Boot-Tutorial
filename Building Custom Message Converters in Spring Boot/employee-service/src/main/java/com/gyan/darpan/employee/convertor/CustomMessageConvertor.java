package com.gyan.darpan.employee.convertor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyan.darpan.employee.model.Custom;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class CustomMessageConvertor implements HttpMessageConverter<Custom> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        boolean canRead = Custom.class.isAssignableFrom(clazz) && mediaType.includes(MediaType.APPLICATION_JSON);

        System.out.println("canRead Method : " + clazz.getSimpleName() + " : " + mediaType.toString() + " : " + canRead);

        return canRead;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        boolean canWrite = Custom.class.isAssignableFrom(clazz) && mediaType.includes(MediaType.APPLICATION_JSON);

        System.out.println("canWrite Method : " + clazz.getSimpleName() + " : " + mediaType.toString() + " : " + canWrite);

        return canWrite;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.APPLICATION_JSON);
    }

    @Override
    public Custom read(Class<? extends Custom> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        System.out.println("read method called");

        InputStream inputStream = inputMessage.getBody();

        return objectMapper.readValue(inputStream, Custom.class);

    }

    @Override
    public void write(Custom custom, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        System.out.println("write method called");


        HttpHeaders headers = outputMessage.getHeaders();

        headers.setContentType(contentType);


        OutputStream outputStream = outputMessage.getBody();

        objectMapper.writeValue(outputStream, custom);
    }
}
