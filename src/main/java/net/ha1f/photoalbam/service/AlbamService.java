package net.ha1f.photoalbam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ha1f.photoalbam.model.Albam;
import net.ha1f.photoalbam.repository.AlbamRepository;

@Service
public class AlbamService {

    @Autowired
    AlbamRepository repository;

    public List<Albam> findAll() {
        return repository.findAll();
    }

}
