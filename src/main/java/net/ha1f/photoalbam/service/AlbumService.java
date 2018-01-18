package net.ha1f.photoalbam.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.ha1f.photoalbam.model.Album;
import net.ha1f.photoalbam.model.Photo;
import net.ha1f.photoalbam.repository.AlbumRepository;

import com.google.common.collect.ImmutableList;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private HashService hashService;

    public List<Album> findAll() {
        return repository.findAll();
    }

    public Album findById(String albumId) {
        return repository.findOne(albumId);
    }

    public void deleteById(String albumId) {
        repository.delete(albumId);
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
            WriteResult result = appendPhotoId(albumId, _photoId);
            System.out.println("photo append, " + result.getN());
            if (result.getN() < 0) {
                photoService.deleteImage(_photoId);
                return false;
            }
            return true;
        }).orElse(false);
    }

    private WriteResult appendPhotoId(String albumId, String photoId) {
        return appendPhotoIds(albumId, ImmutableList.of(photoId));
    }

    private WriteResult appendPhotoIds(String albumId, List<String> photoIds) {
        DBObject queryObject = Query.query(Criteria.where("_id").is(new ObjectId(albumId))).getQueryObject();
        DBObject updateObject = new Update().pushAll("photoIds", photoIds.toArray()).getUpdateObject();
        return mongoTemplate.getCollection(mongoTemplate.getCollectionName(Album.class))
                            .update(queryObject, updateObject);
    }

    public List<Photo> getPhotos(Album album) {
        return ImmutableList.copyOf(photoService.findAll(album.getPhotoIds()));
    }

}
