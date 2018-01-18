package net.ha1f.photoalbam.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "teams")
public class Team {
    @Id
    String id;

    String name;

    List<String> memberIds;
}
