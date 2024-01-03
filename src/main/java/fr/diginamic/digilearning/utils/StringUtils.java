package fr.diginamic.digilearning.utils;

import fr.diginamic.digilearning.exception.BrokenRuleException;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class StringUtils {

    public static boolean isDigits(String str){
        for(int i = 0; i < str.length(); i++){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return str.length() > 0;
    }

    public static Optional<Integer> getIndexOfCounterIfPresent(String fileName){
        for(int i = 0; i < fileName.length(); i++){
            if(fileName.charAt(i) == '_') {
                for(int j = i + 1; j < fileName.length() && Character.isDigit(fileName.charAt(j)); j++){
                    if(j == fileName.length() - 1){
                        return Optional.of(i);
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static String determineFileName(String fileName, String extension, Path directoryPath) {

        if(fileName == null){
            throw new BrokenRuleException("Le nom du fichier doit obligatoirement être précisé");
        }

        int fileVersion = 0;
        String originalFileName = fileName;
        while (Files.exists(Path.of(directoryPath + "/" + fileName + extension))){
            fileVersion++;
            fileName = originalFileName + "_" + fileVersion;
        }
        fileName = fileName.replace(' ', '_');
        return fileName + extension;
    }
}
