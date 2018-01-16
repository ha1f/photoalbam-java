package net.ha1f.photoalbam.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
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

    private Optional<String> extensionFromContentType(String contentType) {
        if ("image/jpeg".equals(contentType)) {
            return Optional.of("jpg");
        } else if ("image/png".equals(contentType)) {
            return Optional.of("png");
        } else if ("image/gif".equals(contentType)) {
            return Optional.of("gif");
        } else if ("image/bmp".equals(contentType)) {
            return Optional.of("bmp");
        }
        return Optional.empty();
    }

    public Photo findById(String photoId) {
        return repository.findOne(photoId);
    }

    public String addImage(MultipartFile multipartFile) {
        Optional<String> extension = extensionFromContentType(multipartFile.getContentType());
        if (!extension.isPresent()) {
            return "";
        }

        Path filePath = generateFilePath("." + extension.get());

        try (OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE)) {
            os.write(multipartFile.getBytes());
            Photo photo = new Photo();
            photo.setFilePath(filePath.toString());
            repository.insert(photo);
            return photo.getId();
        } catch (IOException ex) {
            System.err.println(ex);
            return "";
        }
    }
}
