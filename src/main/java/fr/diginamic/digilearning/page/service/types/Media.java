package fr.diginamic.digilearning.page.service.types;

import lombok.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;

import java.awt.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Media {

    private FileSystemResource resource;
    private MediaType mediaType;
    private String nomRessource;
}
