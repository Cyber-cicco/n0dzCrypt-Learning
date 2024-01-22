package fr.diginamic.digilearning.page;

import fr.diginamic.digilearning.service.PhotoService;
import fr.diginamic.digilearning.service.types.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
@RequestMapping("/photo")
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping
    public ResponseEntity<byte[]> getPhoto(@RequestParam("name") String nomPhoto) throws IOException {
        Media photo = photoService.getPhoto(nomPhoto);
        ContentDisposition contentDisposition = ContentDisposition.inline()
                .filename(photo.getNomRessource(), StandardCharsets.UTF_8)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        headers.setContentType(photo.getMediaType());
        return ResponseEntity.ok()
                .headers(headers)
                .body(photo.getResource().getContentAsByteArray());
    }
}
