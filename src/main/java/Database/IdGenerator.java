package edu.kit.sose2017go4.goprototype.helper;

import java.util.HashMap;

import java.util.Map;

import java.util.UUID;


/**
 * used to generate universal unique ids
 * in order to generate ids with supposed characteristic,
 * the java class java.util.UUID will be used,
 * which can generate a hexadecimal universal unique id with 32 characters.
 * In addition UUID will use java class java.security.SecureRandom for random,
 * which can guarantee the strength of cryptography.
 * After conversion into 62 system, which be build up with digits, lower -and upper case letter,
 * a universal unique ids with 19 characters will be finally generated
 *
 */
public class IdGenerator {


    /**
     * the characters could be used in Id
     */
    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',

            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',

            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',

            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',

            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',

            'Z' };


    /**
     * The constant digitMap for Translation from Character to Integer
     */
    final static Map<Character, Integer> digitMap = new HashMap<Character, Integer>();



    static {

        for (int i = 0; i < digits.length; i++) {

            digitMap.put(digits[i], (int) i);

        }

    }


    /**
     * The constant MAX_RADIX for maximal radix
     */
    public static final int MAX_RADIX = digits.length;


    /**
     * The constant MIN_RADIX for minimal radix
     */
    public static final int MIN_RADIX = 2;


    /**
     * translate a decimal number to a string with the given system
     *
     * @param i     a decimal number which should be translated to string
     * @param radix the radix be used
     * @return the string after translation
     */
    public static String toString(long i, int radix) {

        if (radix < MIN_RADIX || radix > MAX_RADIX)

            radix = 10;

        if (radix == 10)

            return Long.toString(i);



        final int size = 65;

        int charPos = 64;



        char[] buf = new char[size];

        boolean negative = (i < 0);



        if (!negative) {

            i = -i;

        }



        while (i <= -radix) {

            buf[charPos--] = digits[(int) (-(i % radix))];

            i = i / radix;

        }

        buf[charPos] = digits[(int) (-i)];



        if (negative) {

            buf[--charPos] = '-';

        }



        return new String(buf, charPos, (size - charPos));

    }


    /**
     * For input string number format exception.
     *
     * @param s the input string
     * @return the number format exception
     */
    static NumberFormatException forInputString(String s) {

        return new NumberFormatException("For input string: \"" + s + "\"");

    }


    /**
     * to translate a string with the given system to a decimal number
     *
     * @param s     a string with the given system which should be translated to a decimal number
     * @param radix the radix be used
     * @return a decimal number after translation
     */
    public static long toNumber(String s, int radix) {

        if (s == null) {

            throw new NumberFormatException("null");

        }



        if (radix < MIN_RADIX) {

            throw new NumberFormatException("radix " + radix

                    + " less than Numbers.MIN_RADIX");

        }

        if (radix > MAX_RADIX) {

            throw new NumberFormatException("radix " + radix

                    + " greater than Numbers.MAX_RADIX");

        }



        long result = 0;

        boolean negative = false;

        int i = 0, len = s.length();

        long limit = -Long.MAX_VALUE;

        long multmin;

        Integer digit;



        if (len > 0) {

            char firstChar = s.charAt(0);

            if (firstChar < '0') {

                if (firstChar == '-') {

                    negative = true;

                    limit = Long.MIN_VALUE;

                } else if (firstChar != '+')

                    throw forInputString(s);



                if (len == 1) {

                    throw forInputString(s);

                }

                i++;

            }

            multmin = limit / radix;

            while (i < len) {

                digit = digitMap.get(s.charAt(i++));

                if (digit == null) {

                    throw forInputString(s);

                }

                if (digit < 0) {

                    throw forInputString(s);

                }

                if (result < multmin) {

                    throw forInputString(s);

                }

                result *= radix;

                if (result < limit + digit) {

                    throw forInputString(s);

                }

                result -= digit;

            }

        } else {

            throw forInputString(s);

        }

        return negative ? result : -result;

    }







    private static String digits(long val, int digits) {

        long hi = 1L << (digits * 4);

        return IdGenerator.toString(hi | (val & (hi - 1)), IdGenerator.MAX_RADIX)

                .substring(1);

    }


    /**
     * to generate an universal unique id
     *
     * @return an universal unique id
     */
    public static String uuid() {

        UUID uuid = UUID.randomUUID();

        StringBuilder sb = new StringBuilder();

        sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));

        sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));

        sb.append(digits(uuid.getMostSignificantBits(), 4));

        sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));

        sb.append(digits(uuid.getLeastSignificantBits(), 12));

        return sb.toString();

    }



}