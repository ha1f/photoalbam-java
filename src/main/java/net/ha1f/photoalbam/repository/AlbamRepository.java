package net.ha1f.photoalbam.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.ha1f.photoalbam.model.Albam;

public interface AlbamRepository extends MongoRepository<Albam, String> {
}
