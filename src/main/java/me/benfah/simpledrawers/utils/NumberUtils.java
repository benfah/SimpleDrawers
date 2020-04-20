package me.benfah.simpledrawers.utils;

import java.text.NumberFormat;

public class NumberUtils
{
    private static final String[] NUM_SUFFIXES = new String[] {"", "k", "m", "b", "t"};
    private static final int MAX_LENGTH = 4;

    private static char[] c = new char[] {'k', 'm', 'b', 't'};

    /**
     * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invokation.
     *
     * @param n         the number to format
     * @param iteration in fact this is the class from the array c
     * @return a String representing the number n formatted in a cool looking way.
     */
    private static String coolFormat(double n, int iteration)
    {
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) % 10 == 0;//true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (d < 1000 ? //this determines the class, i.e. 'k', 'm' etc
                ((d > 99.9 || isRound || (!isRound && d > 9.99) ? //this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + c[iteration])
                : coolFormat(d, iteration + 1));

    }

    public static String displayShortNumber(Number number)
    {
        if(number.intValue() < 1000)
        {
            return number.toString();
        }
        return coolFormat(number.doubleValue(), 0);
    }

    public static String displayNumber(Number number)
    {
        return NumberFormat.getIntegerInstance().format(number);
    }
}
