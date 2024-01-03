package fr.diginamic.digilearning.page.service;

import fr.diginamic.digilearning.exception.BrokenRuleException;
import fr.diginamic.digilearning.exception.EntityNotFoundException;
import fr.diginamic.digilearning.exception.UnauthorizedException;
import fr.diginamic.digilearning.page.service.types.Media;
import fr.diginamic.digilearning.security.AuthenticationInfos;
import fr.diginamic.digilearning.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private static final String DEFAULT_DOCUMENT_DIRECTORY = System.getProperty("user.dir") + "/ressources/";
    public String uploadPhoto(MultipartFile file, AuthenticationInfos userInfos) throws IOException {

        boolean isPng = Objects.equals(file.getContentType(), "image/png");
        boolean isJpg = Objects.equals(file.getContentType(), "image/jpeg");

        if(!(isPng || isJpg)) {
            throw new UnauthorizedException("Le fichier doit être au format png ou jpeg");
        }

        String extension = (isPng) ? ".png" : ".jpeg";

        String directoryName = "/public/";
        Path directoryPath = Path.of(DEFAULT_DOCUMENT_DIRECTORY + directoryName);

        if (!Files.isDirectory(directoryPath)){
            Files.createDirectory(directoryPath);
        }

        String fileWithoutExtension = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.'));
        String fileName = StringUtils.determineFileName(fileWithoutExtension, extension, directoryPath);

        file.transferTo(new File(directoryPath + "/" + fileName));
        return fileName;
    }

    public Media getPhoto(String nomPhoto){
        System.out.println("in photo");
        Path photoPath = Path.of(DEFAULT_DOCUMENT_DIRECTORY + "/public/" + nomPhoto);
        Media media = new Media();
        if(!Files.exists(photoPath)) {
            throw new EntityNotFoundException("La photo n'a pas été trouvée");
        }
        String suffix = nomPhoto.substring(nomPhoto.lastIndexOf("."));
        switch (suffix) {
            case ".png" : {
                media.setMediaType(MediaType.IMAGE_PNG);
                break;
            }
            case ".jpeg" : {
                media.setMediaType(MediaType.IMAGE_JPEG);
                break;
            }
            case ".svg" : {
                //TODO : vérifier que cela ne pose aucun problème de sécurité
                media.setMediaType(MediaType.APPLICATION_XML);
                break;
            }
            default :
                System.out.println("default");
                throw new BrokenRuleException("Vous ne pouvez demander autre chose qu'une photo via ce point d'entrée");
        }
        media.setResource(new FileSystemResource(photoPath));
        media.setNomRessource(nomPhoto);
        return media;
    }
}
