package net.ha1f.photoalbam.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteStreams;

@Service
public class PhotoFileService {

    private static final Map<String, String> contentTypeExtensionMap = ImmutableMap.of(
            MediaType.IMAGE_JPEG_VALUE, "jpg",
            MediaType.IMAGE_PNG_VALUE, "png",
            MediaType.IMAGE_GIF_VALUE, "gif"
    );

    private static final Map<String, MediaType> extensionMediaTypeMap = ImmutableMap.of(
            "jpg", MediaType.IMAGE_JPEG,
            "png", MediaType.IMAGE_PNG,
            "gif", MediaType.IMAGE_GIF
    );

    private final String imageFolderPathString = "/Users/st20591/photoalbam/photos";

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

    private Path generateRandomFilePath(String extension) {
        final String filename = UUID.randomUUID().toString();

        Path folderPath = generateFolderPath();

        return folderPath.resolve(filename + extension);
    }

    private static Optional<String> extensionFromContentType(String contentType) {
        return Optional.ofNullable(contentTypeExtensionMap.get(contentType));
    }

    public static Optional<MediaType> mediaTypeFromExtension(String extension) {
        return Optional.ofNullable(extensionMediaTypeMap.get(extension));
    }

    public byte[] readFile(String pathString) {
        try (final InputStream is = Files.newInputStream(Paths.get(pathString))) {
            return ByteStreams.toByteArray(is);
        } catch (IOException e) {
            System.err.println(e);
            return new byte[0];
        }
    }

    public Optional<Path> saveFile(MultipartFile multipartFile) {
        Optional<String> extension = extensionFromContentType(multipartFile.getContentType());
        // 規定のcontentType以外保存しない
        if (!extension.isPresent()) {
            return Optional.empty();
        }

        Path filePath = generateRandomFilePath('.' + extension.get());

        try (OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE)) {
            os.write(multipartFile.getBytes());
            return Optional.of(filePath);
        } catch (IOException ex) {
            System.err.println(ex);
            return Optional.empty();
        }
    }
}
