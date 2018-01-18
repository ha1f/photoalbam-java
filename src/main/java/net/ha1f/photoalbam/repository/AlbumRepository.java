package net.ha1f.photoalbam.repository;

import net.ha1f.photoalbam.model.Album;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlbumRepository extends MongoRepository<Album, String> {
}
