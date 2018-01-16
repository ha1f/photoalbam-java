package net.ha1f.photoalbam.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "photos")
public class Photo {
    @Id
    private String id;

    private String filePath;
}
