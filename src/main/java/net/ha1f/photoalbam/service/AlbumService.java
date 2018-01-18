package net.ha1f.photoalbam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.ha1f.photoalbam.model.Album;
import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.repository.AlbumRepository;

import com.google.common.collect.ImmutableList;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository repository;

    @Autowired
    PhotoService photoService;

    @Autowired
    HashService hashService;

    public List<Album> findAll() {
        return repository.findAll();
    }

    public Album findById(String albamId) {
        return repository.findOne(albamId);
    }

    private static Album buildAlbum(String title, String passwordHash) {
        Album album = new Album();
        album.setTitle(title);
        album.setPasswordHash(passwordHash);
        album.setPhotoIds(ImmutableList.of());
        return album;
    }

    public String create(String title) {
        Album album = buildAlbum(title, null);
        repository.save(album);
        return album.getId();
    }

    public String create(String title, String password) {
        String passwordHash = hashService.calcHashForAlbumPassword(password);
        Album album = buildAlbum(title, passwordHash);
        repository.save(album);
        return album.getId();
    }

    public boolean appendPhoto(String albumId, MultipartFile multipartFile) {
        Optional<String> photoId = photoService.saveImage(multipartFile);
        return photoId.map(_photoId -> {
            Album album = appendPhotoId(albumId, _photoId);
            if (album == null) {
                photoService.deleteImage(_photoId);
                return false;
            }
            return true;
        }).orElse(false);
    }

    private Album appendPhotoId(String albumId, String photoId) {
        return appendPhotoIds(albumId, ImmutableList.of(photoId));
    }

    private Album appendPhotoIds(String albumId, List<String> photoIds) {
        final Album album = repository.findOne(albumId);
        if (album == null) {
            return null;
        }
        List<String> newPhotoIds = album.getPhotoIds();
        newPhotoIds.addAll(photoIds);
        album.setPhotoIds(newPhotoIds);
        repository.save(album);
        return album;
    }

    public List<Photo> getPhotos(Album album) {
        return ImmutableList.copyOf(photoService.findAll(album.getPhotoIds()));
    }

}
