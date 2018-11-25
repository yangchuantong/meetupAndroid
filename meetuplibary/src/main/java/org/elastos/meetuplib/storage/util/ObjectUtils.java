package org.elastos.meetuplib.storage.util;

public class ObjectUtils {
    public static boolean hasBlank(Object... strings) {
        for (Object string : strings) {
            if (isBlank(string)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBlank(Object string) {
        return string == null || string.toString().trim().equals("");
    }
}
