package net.ha1f.photoalbam.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.ha1f.photoalbam.model.Team;

public interface TeamRepository extends MongoRepository<Team, String> {
}
