package net.ha1f.photoalbam.model;

import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "photos")
public class Photo {
    @Id
    private String id;

    private String filePath;

    public String getFileName() {
        return Paths.get(filePath).getFileName().toString();
    }

    public Optional<String> getExtension() {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex < 0) {
            return Optional.empty();
        }
        int extensionIndex = dotIndex + 1;
        if (extensionIndex >= filePath.length()) {
            return Optional.empty();
        }
        return Optional.of(filePath.substring(extensionIndex));
    }
}
