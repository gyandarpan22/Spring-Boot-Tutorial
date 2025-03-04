package com.gyan.darpan.employee.service.impl;

import com.gyan.darpan.employee.service.FileStorage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class LocalFileStorage implements FileStorage {
    @Override
    public String storeFile() {
        return "Storing file on local system";
    }
}
