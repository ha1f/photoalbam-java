package net.ha1f.photoalbam.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.ha1f.photoalbam.model.Photo;

public interface PhotoRepository extends MongoRepository<Photo, String> {
}
