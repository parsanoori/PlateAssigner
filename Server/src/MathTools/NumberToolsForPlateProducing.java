package MathTools;

public class NumberToolsForPlateProducing {

    public static Integer addOnesToEnd(int number)
    {
        int result = number == 0 ? number+1 : number;
        int countOfDigits = (int) Math.log10(result) + 1;
        for (int i = countOfDigits ; i < 5 ; i++)
            result += (int) Math.pow(10,i);
        return result;
    }

    public static int replace0with1(int number)
    {
        return number += calculateAddedValue(number);
    }

    private static int calculateAddedValue(int number)
    {
        int result = 0;

        int decimalPlace = 1;

        if (number == 0) {
            result += (1 * decimalPlace);
        }

        while (number > 0) {
            if (number % 10 == 0)
                result += (1 * decimalPlace);

            number /= 10;
            decimalPlace *= 10;
        }
        return result;
    }
}
