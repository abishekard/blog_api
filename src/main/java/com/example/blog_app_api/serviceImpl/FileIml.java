package com.example.blog_app_api.serviceImpl;

import com.example.blog_app_api.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileIml implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile multipartFile) throws IOException {

        String name = multipartFile.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
        String filePath = path+ File.separator+fileName1;

        File f = new File(path);
        if(!f.exists())
            f.mkdir();
        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+File.separator+fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
