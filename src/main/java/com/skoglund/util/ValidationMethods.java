package com.skoglund.util;

import com.skoglund.util.exceptions.EmptyFieldException;
import com.skoglund.util.exceptions.InvalidInputFormatException;
import com.skoglund.util.exceptions.NegativeInputValueException;

public class ValidationMethods {

    public static void validateItemBaseInfo(String brand, String color){
        if(brand.isEmpty() || color.isEmpty()){
            throw new EmptyFieldException("Inget textfält får lämnas tomt!");
        }
        String regex = "^[A-Za-zÅÄÖåäö]*$";
        if(!color.matches(regex)){
            throw new InvalidInputFormatException("Textfältet: färg får endast " +
                    "innehålla bokstäver");
        }

    }

    public static void validateMemberName(String nameInput){
        if(nameInput.isEmpty()){
            throw new EmptyFieldException("Namn textfältet får ej vara tomt");
        }
        if(!nameInput.matches("^[A-Za-zÅÄÖåäö ]*$")){
            throw new InvalidInputFormatException("Namnet får endast innehålla bokstäver och mellanslag");
        }
    }

    public static void validateNewFishingPole(String brand, String color, String rodLength,
                                              String castingWeight, String rodType) {
        if (brand.isEmpty() || color.isEmpty() || rodType.isEmpty() || rodLength.isEmpty() ||
                castingWeight.isEmpty()){
            throw new EmptyFieldException("Inget textfält får lämnas tomt!");
        }
        String regex = "^[A-Za-zÅÄÖåäö]*$";
        if (!color.matches(regex) || !rodType.matches(regex)) {
            throw new InvalidInputFormatException("Textfälten: färg och spötyp får endast " +
                    "innehålla bokstäver");
        }
        int castingWeightToInt;
        double rodLengthToDouble;
        try {
            castingWeightToInt = Integer.parseInt(castingWeight);
        } catch (NumberFormatException e) {
            throw new InvalidInputFormatException("Textfältet för kastvikt får endast beskrivas " +
                    "med ett heltal");
        }
        try{
            rodLengthToDouble = Double.parseDouble(rodLength);
        }catch(NumberFormatException e){
            throw new InvalidInputFormatException("Textfältet för spölängd får endast beskrivas " +
                    "med ett decimaltal");
        }

        if (rodLengthToDouble <= 0 || castingWeightToInt <= 0) {
            throw new NegativeInputValueException("Spö längden och kastvikten måste vara positiva tal");
        }
    }

    public static void validateNewFishingReel(String brand, String color, String gearRatio, String reelType,
                                       String maxDrag){
        if(brand.isEmpty() || color.isEmpty() || gearRatio.isEmpty() || reelType.isEmpty() ||
                maxDrag.isEmpty()){
            throw new EmptyFieldException("Inget textfält får lämnas tomt!");
        }
        String regex = "^[A-Za-zÅÄÖåäö]*$";
        if(!color.matches(regex) || !reelType.matches(regex)){
            throw new InvalidInputFormatException("Textfälten: färg och rullestyp får endast innehålla " +
                    "bokstäver");
        }
        int maxDragToInt;
        try{
            maxDragToInt = Integer.parseInt(maxDrag);
        }catch(NumberFormatException e){
            throw new InvalidInputFormatException("Textfältet för maxbroms får endast innehålla ett" +
                    " heltal");
        }
        if(maxDragToInt <= 0){
            throw new NegativeInputValueException("Maxbromsen kan ej vara ett negativt tal");
        }
    }

    public static void validateNewLureSet(String brand, String color, String lureType){
        if(brand.isEmpty() || color.isEmpty() || lureType.isEmpty()){
            throw new EmptyFieldException("Inget av textfälten får lämnas tomma");
        }
        String regex = "^[A-Za-zÅÄÖåäö]*$";
        if(!color.matches(regex) || !lureType.matches(regex)){
            throw new InvalidInputFormatException("Textfälten: färg och betestyp får endast innehålla " +
                    "bokstäver");
        }

    }
}
