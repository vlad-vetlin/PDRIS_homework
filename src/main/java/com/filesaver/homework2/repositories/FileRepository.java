package com.filesaver.homework2.repositories;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FileRepository {
    private final Path rootPath = Paths.get("files");

    public void saveFile(String name, MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), rootPath.resolve(name));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Сохранение файла " + file.getName() + " не удалось. " + e.getMessage());
        }
    }

    public Resource getFile(String name) {
        try {
            Resource resource = new UrlResource(rootPath.resolve(name).toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new RuntimeException("Файл " + name + " не может быть прочитан");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<String> files() {
        try {
            return Files.list(rootPath).map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Корневого диалога не существует");
        }
    }
}
