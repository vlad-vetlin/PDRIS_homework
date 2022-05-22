package com.filesaver.homework2.controllers;

import com.filesaver.homework2.repositories.FileRepository;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/admin")
public class MainController {

    private final FileRepository fileRepository;

    public MainController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @RequestMapping(path = "/file", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void saveFile(
            @RequestParam String filename,
            @RequestParam("document") MultipartFile document
    ) {
        fileRepository.saveFile(filename, document);
    }

    @GetMapping("/getFile")
    @ResponseBody
    public ResponseEntity<Resource> getFile(
            @RequestParam String filename
    ) {
        Resource resource = fileRepository.getFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/files")
    public List<String> listFiles()
    {
        return fileRepository.files();
    }
}
