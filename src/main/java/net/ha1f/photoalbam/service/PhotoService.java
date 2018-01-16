package net.ha1f.photoalbam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.repository.PhotoRepository;

@Service
public class PhotoService {

    @Autowired
    PhotoRepository repository;

    public Iterable<Photo> findAll() {
        return repository.findAll();
    }

    public String addSamplePhoto() {
        Photo photo = new Photo();
        photo.setFilePath("http://google.com");
        return addPhoto(photo);
    }

    public String addPhoto(Photo photo) {
        repository.insert(photo);
        return photo.getId();
    }
}
