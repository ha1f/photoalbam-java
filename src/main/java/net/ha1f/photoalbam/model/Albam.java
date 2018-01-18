package net.ha1f.photoalbam.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "albams")
public class Albam {
    @Id
    String id;

    String name;

    String passwordHash;

    List<String> photoIds;

    String teamId;
}
