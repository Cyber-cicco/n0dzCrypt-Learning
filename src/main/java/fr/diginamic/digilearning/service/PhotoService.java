package fr.diginamic.digilearning.service;

import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.service.types.Media;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.StringUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
public class PhotoService {

    @Getter
    private final String documentDir;

    public PhotoService(
            Environment environment
    ) {
        if(environment.getProperty("spring.profiles.active", "dev").equals("prod")){
            documentDir = environment.getProperty("prod.photo", System.getProperty("user.dir") + "/ressources/");
        } else {
            documentDir = System.getProperty("user.dir") + "/ressources/";
        }
    }

    public String uploadPhoto(MultipartFile file, String directoryName, AuthenticationInfos userInfos) throws IOException {

        boolean isPng = Objects.equals(file.getContentType(), "image/png");
        boolean isJpg = Objects.equals(file.getContentType(), "image/jpeg");

        if(!(isPng || isJpg)) {
            throw new UnauthorizedException("Le fichier doit être au format png ou jpeg");
        }

        String extension = (isPng) ? ".png" : ".jpeg";

        Path directoryPath = Path.of(documentDir + directoryName);

        if (!Files.isDirectory(directoryPath)){
            Files.createDirectory(directoryPath);
        }

        String fileWithoutExtension = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.'));
        String fileName = StringUtils.determineFileName(fileWithoutExtension, extension, directoryPath);
        OutputStream outputStream = new FileOutputStream(directoryPath + "/" + fileName);
        Thumbnails.of(file.getInputStream())
                .scale(1)
                .outputQuality(0.5)
                .toOutputStream(outputStream);
        outputStream.close();
        return fileName;
    }

    public Media getPhoto(String sourceDirectory, String nomPhoto){
        Path photoPath = Path.of(documentDir + sourceDirectory + nomPhoto);
        Media media = new Media();
        if(!Files.exists(photoPath)) {
            throw new EntityNotFoundException("La photo n'a pas été trouvée");
        }
        String suffix = nomPhoto.substring(nomPhoto.lastIndexOf("."));
        switch (suffix) {
            case ".png", ".webp" -> media.setMediaType(MediaType.IMAGE_PNG);
            case ".jpeg" ->  media.setMediaType(MediaType.IMAGE_JPEG);
            case ".svg" ->  media.setMediaType(MediaType.APPLICATION_XML);
            default -> throw new BrokenRuleException("Vous ne pouvez demander autre chose qu'une photo via ce point d'entrée");
        }
        media.setResource(new FileSystemResource(photoPath));
        media.setNomRessource(nomPhoto);
        return media;
    }
}
