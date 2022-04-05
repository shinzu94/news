package com.course.news;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.course.news.valueObject.FileValueObject;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class FileController {
    public static final String dirForFile = "./articles";
    private final RestTemplate restTemplate;


    public FileController(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping("files/file_list")
    public String getFileList() throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            return gson.toJson(Stream.of(new File(dirForFile).listFiles())
                    .filter(file -> !file.isDirectory())
                    .map(file -> new FileValueObject(file.getName(), file.length()))
                    .collect(Collectors.toSet()));
        } catch (Exception e) {
            Set<String> fileList = new HashSet<>();
            return gson.toJson(fileList);
        }
    }

    @RequestMapping("files/download")
    public ResponseEntity<byte[]> download(@RequestParam(defaultValue = "") String fileName) {
        File file = new File(dirForFile + "/" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plain"));
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentLength(file.length());
        try {
            return new ResponseEntity<byte[]>(Files.readAllBytes(file.toPath()), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
