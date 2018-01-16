package net.ha1f.photoalbam.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.repository.PhotoRepository;

@Service
public class PhotoService {

    @Autowired
    PhotoRepository repository;

    static final String imageFolderPathString = "/Users/st20591/photoalbam/photos";

    private Path generateFolderPath() {
        Path folderPath = Paths.get(imageFolderPathString);

        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectory(folderPath);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }

        return folderPath;
    }

    private Path generateFilePath(String extension) {
        final String filename = UUID.randomUUID().toString();

        Path folderPath = generateFolderPath();
        Path filePath = folderPath.resolve(filename + extension);

        return filePath;
    }

    public Iterable<Photo> findAll() {
        return repository.findAll();
    }

    public String addImage(MultipartFile multipartFile) {
        Path filePath = generateFilePath(".png");

        try (OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE)) {
            os.write(multipartFile.getBytes());
            Photo photo = new Photo();
            photo.setFilePath(filePath.toString());
            repository.insert(photo);
            return photo.getId();
        } catch (IOException ex) {
            System.err.println(ex);
            return "error";
        }
    }
}
