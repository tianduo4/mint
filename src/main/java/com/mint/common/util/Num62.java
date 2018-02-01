package com.mint.common.util;

public class Num62 {
    public static final char[] N62_CHARS = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z'};

    public static final char[] N36_CHARS = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z'};
    public static final int LONG_N36_LEN = 13;
    public static final int LONG_N62_LEN = 11;

    private static StringBuilder longToNBuf(long l, char[] chars) {
        int upgrade = chars.length;
        StringBuilder result = new StringBuilder();

        while (l > 0L) {
            int last = (int) (l % upgrade);
            result.append(chars[last]);
            l /= upgrade;
        }
        return result;
    }

    public static String longToN62(long l) {
        return longToNBuf(l, N62_CHARS).reverse().toString();
    }

    public static String longToN36(long l) {
        return longToNBuf(l, N36_CHARS).reverse().toString();
    }

    public static String longToN62(long l, int length) {
        StringBuilder sb = longToNBuf(l, N62_CHARS);
        for (int i = sb.length(); i < length; i++) {
            sb.append('0');
        }
        return sb.reverse().toString();
    }

    public static String longToN36(long l, int length) {
        StringBuilder sb = longToNBuf(l, N36_CHARS);
        for (int i = sb.length(); i < length; i++) {
            sb.append('0');
        }
        return sb.reverse().toString();
    }

    public static long n62ToLong(String n62) {
        return nToLong(n62, N62_CHARS);
    }

    public static long n36ToLong(String n36) {
        return nToLong(n36, N36_CHARS);
    }

    private static long nToLong(String s, char[] chars) {
        char[] nc = s.toCharArray();
        long result = 0L;
        long pow = 1L;
        for (int i = nc.length - 1; i >= 0; pow *= chars.length) {
            int n = findNIndex(nc[i], chars);
            result += n * pow;

            i--;
        }

        return result;
    }

    private static int findNIndex(char c, char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i]) {
                return i;
            }
        }
        throw new RuntimeException("N62(N36)非法字符：" + c);
    }

    public static void main(String[] args) {
        System.out.println(longToN62(9223372036854775807L));
    }
}

