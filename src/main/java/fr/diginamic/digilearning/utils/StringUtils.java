package fr.diginamic.digilearning.utils;

public class StringUtils {

    public static boolean isDigits(String str){
        for(int i = 0; i < str.length(); i++){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
