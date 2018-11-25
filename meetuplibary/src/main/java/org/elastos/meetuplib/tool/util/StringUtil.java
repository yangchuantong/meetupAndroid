package org.elastos.meetuplib.tool.util;

/**
 * @author hb.nie
 * @description
 */
public class StringUtil {
    public static boolean isBlank(String result) {
        return result == null || result.trim().equals("");
    }
}
