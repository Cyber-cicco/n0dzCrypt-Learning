package fr.diginamic.digilearning.utils.reflection;

import fr.diginamic.digilearning.exception.FunctionalException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SqlResultMapper {

    public static <T> T mapToObject(Class<T> _class, String[] results) {
        try{
            Field[] fields = _class.getDeclaredFields();
            T newInstance = _class.newInstance();
            if(fields.length < results.length){
                throw new FunctionalException("La conversion entre les résultats et les objets de la classe n'est pas possible");
            }
            for (int i = 0; i < results.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                if(results[i] == null){
                    continue;
                }
                if (field.getType().equals(String.class)) {
                    field.set(newInstance, results[i]);
                } else if (field.getType().equals(LocalDateTime.class)) {
                    field.set(
                            newInstance,
                            LocalDateTime.parse(results[i].substring(0, results[i].lastIndexOf(".")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                } else if (field.getType().equals(Long.class)){
                    field.set(newInstance, Long.parseLong(results[i]));
                } else if (field.getType().equals(Integer.class)){
                    field.set(newInstance, Integer.parseInt(results[i]));
                } else if (field.getType().equals(Double.class)){
                    field.set(newInstance, Double.parseDouble(results[i]));
                } else if (field.getType().equals(Boolean.class)) {
                    field.set(newInstance, Boolean.parseBoolean(results[i]));
                }
            }
            return newInstance;
        } catch (InstantiationException | IllegalAccessException e){
            throw new FunctionalException();
        }
    }
}
