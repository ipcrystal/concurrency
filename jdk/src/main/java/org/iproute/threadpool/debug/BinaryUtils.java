package org.iproute.threadpool.debug;

/**
 * BinaryUtils
 *
 * @author tech@intellij.io
 */
public class BinaryUtils {

    public static String intToBinaryString(int number) {
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(number));
        int length = binary.length();
        if (length < 32) {
            for (int i = 0; i < 32 - length; i++) {
                binary.insert(0, "0");
            }
        }
        return pretty(binary.toString());
    }

    private static String pretty(String intBinary) {
        return intBinary.substring(0, 8) +
                "," +
                intBinary.substring(8, 16) +
                "," +
                intBinary.substring(16, 24) +
                "," +
                intBinary.substring(24);
    }

}
