package Core;

import Exceptions.PlateOutOfBoundExcepetion;
import MathTools.NumberToolsForPlateProducing;

import java.util.HashMap;

public class PlateIDProducer {
    public enum Scope{ Normal , Taxi , Public }

    private static final char[] normalPlates = { 'آ', 'ب', 'پ', 'ث', 'ج', 'چ', 'ح', 'خ', 'د',
            'ذ', 'ر', 'ز', 'ژ', 'س', 'ش', 'ص', 'ض', 'ط', 'ظ', 'غ', 'ف', 'ق',
            'ک', 'گ', 'ل', 'م', 'ن', 'و', 'ه', 'ی' };

    private static int indexOfNormalPlateLetter = 0;

    private static HashMap<Scope,Integer> scopeToInteger = new HashMap<>();

    static {
        scopeToInteger.put(Scope.Normal,11110);
        scopeToInteger.put(Scope.Taxi,11110);
        scopeToInteger.put(Scope.Public,11110);
    }

    public static String whatIsNextPlate(Scope scope) throws PlateOutOfBoundExcepetion {
        Integer number = nextNumber(scope);
        return nextPlateID(scope,number);
    }

    public static String getNewPlateID(Scope scope) throws PlateOutOfBoundExcepetion {
        Integer number = nextNumber(scope);
        String plate = nextPlateID(scope,number);
        scopeToInteger.replace(scope,number);
        if (scope == Scope.Normal && scopeToInteger.get(scope) == 99999)
            indexOfNormalPlateLetter++;
        return plate;
    }

    private static String nextPlateID(Scope scope, Integer number) throws PlateOutOfBoundExcepetion {
        String result = switch (scope){
            case Normal -> String.valueOf(number/1000) + nextLetterForNormalPlates() + String.valueOf(number%1000);
            case Taxi -> String.valueOf(number/1000) + 'ت' + String.valueOf(number%1000);
            case Public -> String.valueOf(number/1000) + 'ع' + String.valueOf(number%1000);
        };
        return result.chars().map(c -> {
            if (c <= '9' && c >= '1')
                return c += '۱' - '1';
            return c;
        }).collect(StringBuilder::new,			// supplier
                StringBuilder::appendCodePoint, // accumulator
                StringBuilder::append)			// combiner
                .toString();
    }

    private static char nextLetterForNormalPlates() throws PlateOutOfBoundExcepetion {
        if (scopeToInteger.get(Scope.Normal) == 99999)
            if (indexOfNormalPlateLetter == normalPlates.length - 1)
                throw new PlateOutOfBoundExcepetion();
            else
                return normalPlates[indexOfNormalPlateLetter+1];
        return normalPlates[indexOfNormalPlateLetter];
    }

    private static String createPlateNumberString(Integer number){
        return String.valueOf(number);
    }

    private static Integer nextNumber(Scope scope) throws PlateOutOfBoundExcepetion {
        if ((scope == Scope.Public || scope == Scope.Taxi) && scopeToInteger.get(scope) == 99999)
            throw new PlateOutOfBoundExcepetion();
        Integer current = scopeToInteger.get(scope);
        current = (current+1) % 100000;
        current = NumberToolsForPlateProducing.addOnesToEnd(current);
        current = NumberToolsForPlateProducing.replace0with1(current);
        return current;
    }



}
