package net.ha1f.photoalbam.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.repository.PhotoRepository;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository repository;

    @Autowired
    private PhotoFileService fileService;

    public Iterable<Photo> findAll() {
        return repository.findAll();
    }

    public Iterable<Photo> findAll(Iterable<String> ids) {
        return repository.findAll(ids);
    }

    public Optional<Photo> findById(String photoId) {
        return Optional.ofNullable(repository.findOne(photoId));
    }

    public Optional<MediaType> getMediaType(Photo photo) {
        return photo.getExtension()
                    .flatMap(PhotoFileService::mediaTypeFromExtension);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public byte[] fetchImageData(Photo photo) {
        return fileService.readFile(photo.getFilePath());
    }

    public boolean deleteImage(String photoId) {
        Photo photo = repository.findOne(photoId);
        if (photo == null) {
            return false;
        }
        return fileService.deleteFile(photo.getFilePath());
    }

    public Optional<String> saveImage(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return Optional.empty();
        }
        return fileService.saveFile(multipartFile)
                          .map(path -> {
                              Photo photo = new Photo();
                              photo.setFilePath(path.toString());
                              repository.insert(photo);
                              return photo.getId();
                          });
    }
}
