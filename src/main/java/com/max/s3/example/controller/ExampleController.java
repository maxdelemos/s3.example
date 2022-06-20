package com.max.s3.example.controller;

import com.max.s3.example.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class ExampleController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam(value = "file") MultipartFile file)
    {
        String response = storageService.uploadFile(file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> download(@RequestParam(value = "fileName") String fileName) throws IOException {
        byte[] response = storageService.downloadFile(fileName);
        ByteArrayResource byteArrayResource = new ByteArrayResource(response);
        return ResponseEntity
                .ok()
                .contentLength(response.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"" )
                .body(byteArrayResource);
    }

    @GetMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam(value = "fileName") String fileName)
    {
        String response = storageService.deleteFile(fileName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
