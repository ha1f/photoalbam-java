package net.ha1f.photoalbam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ha1f.photoalbam.model.Albam;
import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.repository.AlbamRepository;
import net.ha1f.photoalbam.repository.PhotoRepository;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;

@Service
public class AlbamService {

    @Autowired
    AlbamRepository repository;

    @Autowired
    PhotoRepository photoRepository;

    @NonNull
    public List<Albam> findAll() {
        return repository.findAll();
    }

    public Albam findById(String albamId) {
        return repository.findOne(albamId);
    }

    public List<Photo> getPhotos(Albam albam) {
        return ImmutableList.copyOf(photoRepository.findAll(albam.getPhotoIds()));
    }

    public void appendPhotos(Albam albam, List<String> additionalPhotoIds) {
        // albam.setPhotoIds(photoIds);
        repository.save(albam);
    }

}
