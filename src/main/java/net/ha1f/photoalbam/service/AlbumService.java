package net.ha1f.photoalbam.service;

import java.util.List;

import net.ha1f.photoalbam.model.Album;
import net.ha1f.photoalbam.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Nullable;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository repository;

    public List<Album> findAll() {
        return repository.findAll();
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

}
