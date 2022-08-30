package com.yw.task.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 19位UUID生成工具类
 *
 * @author yanzhitao@xiaomalixing.com
 */
public class UUIDExtUtil {

    private UUIDExtUtil() {
        throw new IllegalStateException("Utility class");
    }

    static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    static final Map<Character, Integer> DIGIT_MAP = new HashMap<>();

    /**
     * 十进制
     */
    public static final int DECIMAL_SYSTEM_SYSTEM_OF_NUMERATION = 10;

    public static final char ZERO_CHAR = '0';
    public static final char MINUS_SIGN_CHAR = '-';
    public static final char PLUS_SIGN_CHAR = '+';

    static {
        for (int i = 0; i < DIGITS.length; i++) {
            DIGIT_MAP.put(DIGITS[i], i);
        }
    }

    /**
     * 支持的最大进制数
     */
    public static final int MAX_RADIX = DIGITS.length;

    /**
     * 支持的最小进制数
     */
    public static final int MIN_RADIX = 2;

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return toString(hi | (val & (hi - 1)), MAX_RADIX)
                .substring(1);
    }

    /**
     * 以62进制（字母加数字）生成19位UUID，最短的UUID
     *
     * @return 19位UUID
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid(uuid);
    }

    /**
     * 以62进制（字母加数字）生成19位UUID，最短的UUID
     *
     * @return 19位UUID
     */
    public static String uuid(UUID uuid) {
        return digits(uuid.getMostSignificantBits() >> 32, 8) +
                digits(uuid.getMostSignificantBits() >> 16, 4) +
                digits(uuid.getMostSignificantBits(), 4) +
                digits(uuid.getLeastSignificantBits() >> 48, 4) +
                digits(uuid.getLeastSignificantBits(), 12);
    }

    /**
     * 将长整型数值转换为指定的进制数（最大支持62进制，字母数字已经用尽）
     *
     * @param i                  长整型数值
     * @param systemOfNumeration 进制
     * @return 对应进制数
     */
    private static String toString(long i, int systemOfNumeration) {
        if (systemOfNumeration < MIN_RADIX || systemOfNumeration > MAX_RADIX) {
            systemOfNumeration = DECIMAL_SYSTEM_SYSTEM_OF_NUMERATION;
        }
        if (systemOfNumeration == DECIMAL_SYSTEM_SYSTEM_OF_NUMERATION) {
            return Long.toString(i);
        }

        final int size = 65;
        int charPos = 64;

        char[] buf = new char[size];
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }

        while (i <= -systemOfNumeration) {
            buf[charPos--] = DIGITS[(int) (-(i % systemOfNumeration))];
            i = i / systemOfNumeration;
        }
        buf[charPos] = DIGITS[(int) (-i)];

        if (negative) {
            buf[--charPos] = MINUS_SIGN_CHAR;
        }

        return new String(buf, charPos, (size - charPos));
    }

}
