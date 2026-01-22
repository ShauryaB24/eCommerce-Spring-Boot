package com.shaurya.Ecommerce_sb.service.impl;

import com.shaurya.Ecommerce_sb.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // Get the file names / original file
        String originalFileName = file.getOriginalFilename();

        // Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        // mat.jpg(originalFileName) => 1234(randomId) -> 1234.jpg(fileName)
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;  //pathSeparator = "/"

        // Check if path exist and create
//        String projectRoot = System.getProperty("user.dir");
//        File folder = new File(projectRoot, path);
//
//        if (!folder.exists()) {
//            folder.mkdir();
//        }
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();

        // Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }
}
