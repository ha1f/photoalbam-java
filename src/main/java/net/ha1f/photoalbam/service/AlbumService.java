package net.ha1f.photoalbam.service;

import java.util.List;

import net.ha1f.photoalbam.model.Album;
import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.repository.AlbumRepository;
import net.ha1f.photoalbam.repository.PhotoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository repository;

    @Autowired
    PhotoRepository photoRepository;

    public List<Album> findAll() {
        return repository.findAll();
    }

    public Album findById(String albamId) {
        return repository.findOne(albamId);
    }

    @Nullable
    public Album appendPhotoIds(String albamId, List<String> photoIds) {
        final Album album = repository.findOne(albamId);
        if (album == null) {
            return null;
        }
        List<String> newPhotoIds = album.getPhotoIds();
        newPhotoIds.addAll(photoIds);
        album.setPhotoIds(newPhotoIds);
        repository.save(album);
        return album;
    }

    public List<Photo> getPhotos(Album albam) {
        return ImmutableList.copyOf(photoRepository.findAll(albam.getPhotoIds()));
    }

}
